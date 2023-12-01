package oy.tol.tra;


public class TreeNode<K extends Comparable<K>,V> {
    public static int addDepth;
    public static int collisionChainLength;
    private int hash;
    private Pair <K,V> keyValue;
    private TreeNode <K,V> leftChild;
    private TreeNode <K,V> rightChild;
    private LinkedListImplementation<Pair<K, V>> collisionChain = null;
    
    public TreeNode(K key, V value) {
        this.keyValue = new Pair<K,V>(key,value);
        this.leftChild = null;
        this.rightChild = null;
        this.hash = key.hashCode();
        this.collisionChain = null;
    }

    public boolean insert(K key, V value, int keyToSearch) {
        boolean added = false;
        if (keyToSearch < this.hash) {
            if (null == leftChild) {
                leftChild = new TreeNode<>(key, value);
                added = true;
            } else {
                added = leftChild.insert(key, value, keyToSearch);
            }
            TreeNode.addDepth++;
        } else if (keyToSearch > this.hash) {
            if (null == rightChild) {
                rightChild = new TreeNode<>(key, value);
                added = true;
            } else {
                added = rightChild.insert(key, value, keyToSearch);
            }
            TreeNode.addDepth++;
        } else {
            if (keyValue.getKey().equals(key)) {
                keyValue.setvalue(value);
            } else {
                if (null == collisionChain) {
                    collisionChain = new LinkedListImplementation<>();
                    collisionChain.add(new Pair<>(key, value));
                    added = true;
                    TreeNode.collisionChainLength = 1;
                } else {
                    Pair<K,V> toSearch = new Pair<>(key, value);
                    int index = collisionChain.indexOf(toSearch);
                    if (index < 0) {
                        collisionChain.add(new Pair<>(key, value));
                        added = true;
                    } else {
                        collisionChain.remove(index);
                        collisionChain.add(new Pair<>(key, value));
                    }
                    TreeNode.collisionChainLength = collisionChain.size();
                }
            }
        }
        return added;
    }

    V find(K key, int toFindHash){
        V result = null;
        if(toFindHash > this.hash) {
            if(null != rightChild){
                result = rightChild.find(key, toFindHash);
            }
        } else if (toFindHash < this.hash) {
            if(null != leftChild){
                result = leftChild.find(key, toFindHash);
            }
        } else {
            if (keyValue.getKey().equals(key)) {
                return keyValue.getValue();
            } else {
                if(null != collisionChain) {
                    Pair<K, V> toSearch = new Pair<>(key, keyValue.getValue());
                    int index = collisionChain.indexOf(toSearch);
                    if(index >= 0) {
                        return collisionChain.get(index).getValue();
                    }

                }
            }
        }
        return result;
    }

    public void toSortedArray(Pair<K, V>[] array, int [] toAddIndex) {
        if(null != leftChild){
            leftChild.toSortedArray(array, toAddIndex);
        }
        array[toAddIndex[0]++] = new Pair<K,V>(keyValue.getKey(), keyValue.getValue());
        if(null != collisionChain) {
            for(int index = 0; index < collisionChain.size(); index++){
                Pair<K, V> found = collisionChain.get(index);
                if(null != found){
                    array[toAddIndex[0]++] = new Pair<K,V>(found.getKey(), found.getValue());
                }
            }
        }
        if(null != rightChild){
            rightChild.toSortedArray(array, toAddIndex);
        }
    }
}






package oy.tol.tra;


public class KeyValueBSearchTree<K extends Comparable<K>,V> implements Dictionary<K, V> {
    private int count;
    private int maxDepth;
    private int maxCollisionChainLength;
    private TreeNode<K, V> root;

    // This is the BST implementation, KeyValueHashTable has the hash table implementation

    @Override
    public Type getType() {
       return Type.BST;
    }
 
    @Override
    public int size() {
       return count;
    }

    /**
     * Prints out the statistics of the tree structure usage.
     * Here you should print out member variable information which tell something about
     * your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member variables of the class
     * (int counters) in add(K) whenever a collision happen. Then print this counter value here. 
     * You will then see if you have too many collisions. It will tell you that your hash function
     * is good or bad (too much collisions against data size).
     */
    @Override
    public String getStatus() {
      String toString = "count of the tree nodes: " + count + "\n";
      toString += "Max tree depth: " + maxDepth + "\n";
      toString += "max collision chain: " + maxCollisionChainLength + "\n";
      return toString;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        if(key == null || value == null){
            throw new IllegalArgumentException("key or value can't be null");
        }
        boolean added = false;
        if(null == root) {
            root = new TreeNode<K,V>(key, value);
            count++;
            maxDepth = 1;
            maxCollisionChainLength = 0;
        } else {
            TreeNode.addDepth = 1;
            maxCollisionChainLength = 0;
            added = root.insert(key, value, key.hashCode());
            if(added){
                count++;  
                maxDepth = Math.max(TreeNode.addDepth, maxDepth);
                maxCollisionChainLength = Math.max(TreeNode.collisionChainLength, maxCollisionChainLength);
            }
        }
        return added;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if(key == null){
            throw new IllegalArgumentException("joo");
        }
        if(null == root){
            return null;
        } else {
            return root.find(key, key.hashCode());
        }
    }

    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        //NADA 
       
    }

    @Override
    public Pair<K,V> [] toSortedArray() {
        if(null == root){
            return null;
        }
        Pair<K, V>[] returnArray = (Pair<K,V>[]) new Pair[count];
        int [] toAddIndex = {0};
        root.toSortedArray(returnArray, toAddIndex);
        Algorithms.fastSort(returnArray);
        return returnArray;
      }
    
      @Override
      public void compress() throws OutOfMemoryError {
        // Nada
    }

    public int getCapacity() {
        return Integer.MAX_VALUE;
    }
   
}

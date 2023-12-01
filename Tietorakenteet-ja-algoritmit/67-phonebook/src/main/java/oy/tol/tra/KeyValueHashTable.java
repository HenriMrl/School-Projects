package oy.tol.tra;


public class KeyValueHashTable<K extends Comparable<K>, V> implements Dictionary<K, V> {
    Pair <K,V> [] array;
    private static int DEFAULT_CAPACITY = 20;
    private static double LOAD_FACTOR = 0.25;
    private int capacity;
    private int count;
    private int collisionCount;
    private int maxProbingCount;
    private int reallocationCount;
    
    
    public KeyValueHashTable(int capacity) throws OutOfMemoryError {
        ensureCapacity(capacity);
    }

    public KeyValueHashTable() throws OutOfMemoryError {
        ensureCapacity(DEFAULT_CAPACITY);
    }

    @Override
    public Type getType() {
        return Type.HASHTABLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        if(count == 0){
            array = (Pair<K,V>[])new Pair[size];
            this.capacity = size;
        }else{
            reallocate(size);
        }
    }

    @Override
    public int size() {
       return count;
    }

    /**
     * Prints out the statistics of the hash table.
     * Here you should print out member variable information which tell something
     * about your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member
     * variables of the class (int counters) in add() whenever a collision
     * happen. Then print this counter value here.
     * You will then see if you have too many collisions. It will tell you that your
     * hash function is not good.
     */
    @Override
    public String getStatus() {
        String toString = "Hash table fill factor is " + LOAD_FACTOR + "\n"
    + "Hash table had " + collisionCount + " collisions when filling the hash table.\n"
    + "Hash table had to probe " + maxProbingCount + " times in the worst case.\n"
    + "Current fill rate is: " + ((double)count / (double)capacity * 100);
        return toString;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        if(key == null || value == null) {
            throw new IllegalArgumentException("joop");
        }
        int index = 0;
        int hashModifier = 0;
        int currentProbingCount = 0;
        boolean added = false;
        if (count > capacity * LOAD_FACTOR){
            reallocate((int)(capacity * (1.0 / LOAD_FACTOR)));
        }
        do {
            index = indexFor(key, hashModifier);
            if(array[index] == null){
                array[index] = new Pair <K, V>(key, value);
                added = true;
                count++;
            }else if (!array[index].getKey().equals(key)){
                hashModifier++;
                collisionCount++;
                currentProbingCount++;
            }
        } while (!added);
        if(maxProbingCount < currentProbingCount){
            maxProbingCount = currentProbingCount;
        }
        return true;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if(key == null){
            throw new IllegalArgumentException("joop");
        }
        boolean finished = false;
        V result = null;
        int hashModifier = 0;
       
        do {
            int index = indexFor(key, hashModifier);
            if(array[index] != null){
                if(array[index].getKey().equals(key)){
                   result = array[index].getValue();
                   finished = true;
                } else {
                    hashModifier++;
                }
            } else {
                finished = true;
            }
        } while(!finished);
        return result;
    }

    @Override
    @java.lang.SuppressWarnings({"unchecked"})
    public Pair<K,V> [] toSortedArray() {
        Pair<K, V>[] toReturn = (Pair<K, V>[]) new Pair[count];
        int addIndex = 0;
        for (int index = 0; index < capacity; index++){
            if (array[index] != null) toReturn[addIndex++] = array[index];
        }
        Algorithms.fastSort(toReturn);
        return toReturn;
      }
    
      @java.lang.SuppressWarnings({"unchecked"})
      public void reallocate(int newCapacity) {
          Pair<K, V> [] oldarray = this.array;
          array = (Pair<K, V>[]) new Pair[newCapacity];
          int oldcapacity = capacity;
          collisionCount = 0;
          maxProbingCount = 0;
          count = 0;
          reallocationCount++;
          capacity = newCapacity;
          for (int i = 0; i < oldcapacity; i++) {
              if (oldarray[i] != null) {
                  add(oldarray[i].getKey(), oldarray[i].getValue());
              }
          }
      }

      

    @Override
    public void compress() throws OutOfMemoryError {
        int newCapacity = (int)(count * (1.0 / LOAD_FACTOR));
        if(newCapacity < capacity){
            reallocate(newCapacity);
        }
    }

    private int indexFor(K key, int hashModifier){
        return ((key.hashCode() + hashModifier) & 0x7fffffff) % capacity;
    }


}

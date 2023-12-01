package oy.tol.tra;


public class StackImplementation<E> implements StackInterface<E> {

   private int capacity;
   private int currentIndex;
   private Object [] itemArray;
   private static final int MY_CONSTANT_VARIABLE = 10;

   public StackImplementation() throws StackAllocationException {
      this(MY_CONSTANT_VARIABLE);
   }

   public StackImplementation(int capacity) throws StackAllocationException {
      try {
         itemArray = new Object[capacity];
         this.capacity = capacity;
         this.currentIndex = -1;
         if (capacity < 2){
            throw new StackAllocationException("jotain");
       }
     } catch(Exception e) {
        throw new StackAllocationException("jeps");

      }
   

  }

   
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if(element == null) {
         throw new NullPointerException("ei voi olla null");
      }
      if(currentIndex >= capacity - 1){
         reallocateArray();
      }
      currentIndex++;
      itemArray[currentIndex] = element;
      
   }

   public void reallocateArray() {
     try {
         int newCapasity = capacity * 2;
         Object[] newarray = new Object[newCapasity];
         for(int index = 0; index <= currentIndex; index++){
            newarray[index] = itemArray[index];
         }
         itemArray = newarray;
         capacity = newCapasity;

     } catch(Exception e) {
        throw new StackAllocationException("no room for bigger array");
    }

   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex < 0){
         throw new StackIsEmptyException("cannot pop from empty stack");
      }
      E element = (E)itemArray[currentIndex];
      itemArray[currentIndex] = null;
      currentIndex--;
      return element;    
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex < 0) {
         throw new StackIsEmptyException("cannot peek from empty stack");
      }
      return (E) itemArray[currentIndex];
      
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      currentIndex++;
      for(int index = 0; index < capacity; index++){
         itemArray[index] = null;

         currentIndex = -1;
      }
      
   }

   @Override
   public boolean isEmpty() {
      return currentIndex < 0;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("[");
      for(int index = 0; index <= currentIndex; index++){
         builder.append(itemArray[index]);
         if(index < currentIndex){
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}

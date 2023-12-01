package oy.tol.tra;

public class LinkedListImplementation<E> implements LinkedListInterface<E> {
   
   

   private class Node<T> {
      Node(T data) {
         this.element = data;
         next = null;
      }
      T element;
      Node<T> next;
      @Override
      public String toString() {
         return element.toString();
      }
   }

   private Node<E> head = null;
   private int count;
   
   

   public LinkedListImplementation() throws LinkedListAllocationException {
      head = null;
      count = 0;
   }
 


   @Override
   public void add(E element) throws NullPointerException, LinkedListAllocationException {
      if (null == head){
         head = new Node<E>(element);
      } else {
         Node<E> current = head;
         while(current.next != null) {
            current = current.next;
         }
         current.next = new Node<E>(element);
      }
      count++;
   }

   @Override
   public void add(int index, E element) throws NullPointerException, LinkedListAllocationException, IndexOutOfBoundsException {
      if(index < 0 || index > count){
         throw new IndexOutOfBoundsException("index is wrong for this linked list");
      }
      if (index == 0){
         Node<E> newnode = new Node<E>(element);
         newnode.next = head;
         head = newnode;
         count++;
      } else {
         int counter = 0;
         Node<E> current = head;
         Node<E> previous = null;
         while (counter < index) {
           previous = current;
           current = current.next;
           counter++; 
         }
         Node<E> newnode = new Node<E>(element);
         previous.next = newnode;
         newnode.next = current;
         count++;
      }
   }

   @Override
   public boolean remove(E element) throws NullPointerException {
      if (element == null){
         throw new NullPointerException("element is null");
      }
      if(head == null){
         return false;
      }
      Node<E> current = head;
      Node<E> previous = null;
      if(current != null && current.element != element){
         previous = current;
         current = current.next;
      }
      if(current != null && !previous.element.equals(element)){
         return false;
      } else if(current.element.equals(element)){
         previous.next = current.next;
         count++;
     }
       return false;
   }

   @Override
   public E remove(int index) throws IndexOutOfBoundsException {
      
      if(index < 0 || index >= count) {
         throw new IndexOutOfBoundsException("invalid index to list");
      }
      E removed = null;
      if(index == 0){
         removed = head.element;
         head = head.next;
         count--;
      } else {
         int counter = 1;
         Node<E> current = head.next;
         Node<E> previous = head;
         while (current != null) {
            if (counter == index) {
               removed = current.element;
               previous.next = current.next;
               count--;
               break;
            }
            counter++;
            previous = current;
            current = current.next;
         }
      }
      return removed;
   }

   @Override
   public E get(int index) throws IndexOutOfBoundsException {
      if(index >= count || index < 0) {
         throw new IndexOutOfBoundsException("invalid index to list");
      } else {
        Node<E> current = head;
        for(int i = 0; i < index; i++){
         current = current.next;
        }
        return current.element;
      }    
   }
  

   @Override
   public int indexOf(E element) throws NullPointerException {
      if(element == null){
         throw new NullPointerException("joo");
      }
      if(count == 0){
         return -1;
      }
      int index = 0;
      Node<E> current = head;
      
      while(current != null){
         if(current.element.equals(element)){
            return index;
         }
         current = current.next;
         index++;
      }
      return -1;
   }

   @Override
   public int size() {
      return count;
   }

   @Override
   public void clear() {
      head = null;
      count = 0;
   }

   @Override
   public void reverse() {
      // TODO: implement this only when doing the task explained the TASK-2.md.
      // This method is not needed in doing the task in the README.md.
      Node<E> previous = null;
      Node<E> current = head;
      Node<E> next = head;
      while(next != null){
         current = next;
         next = next.next;
         current.next = previous;
         previous = current;
         head = current;
      }
      
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
        builder.append("[");
        if(count > 0){
            int counter = count;
            Node<E> current = head;
            Node<E> previous = null;
            while(counter > 0) {
               while(current != null){
                  builder.append(current);
                  previous = current;
                  current = current.next;
                  counter--;
                  if (counter > 0){
                     builder.append(", ");
                 }
               }
               break;
            }
         }
        builder.append("]");
        return builder.toString();
   }
   
}

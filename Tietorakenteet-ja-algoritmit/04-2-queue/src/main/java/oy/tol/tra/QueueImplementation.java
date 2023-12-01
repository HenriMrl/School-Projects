package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E> { 

    private Object itemArray[];
    private int head;
    private int tail;
    private int count;
    private int capacity;
    private static final int MY_CONSTANT_VARIABLE = 10;

    public QueueImplementation() throws QueueAllocationException  {
       this(MY_CONSTANT_VARIABLE);
    }

    public QueueImplementation(int capacity) throws QueueAllocationException {
        this.capacity = capacity;
        this.itemArray = new Object[capacity];
        head = 0;
        tail = 0;
        count = 0;
    }

    public void reallocateArray() {
        int newCapasity = capacity * 2;
        Object[] newarray;
        try {
           newarray = new Object[newCapasity];
        } catch(Exception e) {
            throw new QueueAllocationException("not enough room for bigger array");
        }
        int index = head;
        int counter = count;
        int newItemArrayIndex = 0;
        while(counter > 0){
            newarray[newItemArrayIndex++] = itemArray[index];
            index++;
            counter--;
            if(index >= capacity){
                index = 0;
            }

        }
        head = 0;
        tail = count;
        capacity = newCapasity;
        itemArray = newarray;
    }

   @Override
    public void clear() {
        capacity = 10;
        head = 0;
        tail = 0;
        count = 0;
        itemArray = new Object[10];
    }

    @Override
     public E dequeue() {
        if(count == 0){
            throw new QueueIsEmptyException("Cannot dequeue from empty queue");
        } 
        E element = (E) itemArray[head];
        itemArray[head] = null;
        count--;
        head++;
        if (head >= capacity){
            head = 0;
        }
        return element;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        if(count>0){
            int index = head;
            int counter = count;
            while(counter > 0){
                builder.append(itemArray[index++]);
                counter--;
                if (counter > 0){
                    builder.append(", ");
                }
                if (index >= capacity){
                    index = 0;
                }
            }
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int size(){
        return count;
    }

    @Override
    public boolean isEmpty(){
        return count == 0;
    }

    @Override
    public int capacity(){
        return capacity;
    }

    @Override
    public E element() throws QueueIsEmptyException{
        if(isEmpty()){
            throw new QueueIsEmptyException("jono on tyhjä");
        }
        else{
            return (E) itemArray[head];
        }
    }

    @Override
     public void enqueue(E element) throws NullPointerException,QueueAllocationException{
        if(element == null){
            throw new NullPointerException("Elementti on nulli)");
        }

        if(count >= capacity) {
            reallocateArray();
        }

        if(tail >= capacity) {
            tail = 0;
        }

        itemArray[tail++] = element;
        count++;
    }
}

    


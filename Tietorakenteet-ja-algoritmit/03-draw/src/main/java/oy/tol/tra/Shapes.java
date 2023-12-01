package oy.tol.tra;

import java.util.Arrays;
import java.util.function.Predicate;

import java.awt.Graphics;

public class Shapes {

    private Shape[] shapeArray = null;
    private int capacity;
    private int count = 0;
    private ShapesListener listener = null;

    public Shapes() throws ShapeException {
        this(10);
    }

    public Shapes(int size) throws ShapeException {
        if (size < 2) {
            throw new ShapeException("Shapes capacity must be >= 2");
        }
        this.capacity = size;
        shapeArray = new Shape[size];
    }

    public Shapes(final Shapes another) {
        this.capacity = another.capacity;
        this.shapeArray = new Shape[this.capacity];
        this.count = another.count;
        this.shapeArray = Arrays.copyOf(another.shapeArray, another.count);
    }

    public void setListener(ShapesListener listener) {
        this.listener = listener;
    }

    public int count() {
        return count;
    }

    public int capacity() {
        return capacity;
    }

    public void add(final Shape shape) throws NullPointerException, ShapeException {
        if (null == shape) {
            throw new NullPointerException("Cannot add null shapes");
        }
        if (count >= capacity - 1) {
            try {
                reallocate();
            } catch (Exception e) {
                throw new ShapeException("Could not reallocate shape array");
            }
        }
        shapeArray[count++] = shape;
        if (null != listener) {
            listener.shapesChanged();
        }
    }

    public void removeAll() {
        this.capacity = 10;
        this.count = 0;
        shapeArray = new Shape[10];
    }

    public void remove(final Shape shape) throws NullPointerException, InterruptedException {
        if (null == shape)
            throw new NullPointerException("Cannot remove null shapes");
        boolean found = false;
        int index = 0;
        for (; index < shapeArray.length && shapeArray[index] != null; index++) {
            if (shape.id == shapeArray[index].id) {
                shapeArray[index] = null;
                found = true;
                count--;
                break;
            }
        }
        if (found) {
            if (null != listener) {
                listener.shapesChanged();
            }
            moveElementsStepDownFrom(index + 1);
        }
    }

    public void remove(final int index) throws ArrayIndexOutOfBoundsException, InterruptedException {
        if (index < 0 || index > count - 1) {
            throw new ArrayIndexOutOfBoundsException("Attempting to access shapesArray out of bounds.");
        }
        shapeArray[index] = null;
        if (null != listener) {
            listener.shapesChanged();
        }
        moveElementsStepDownFrom(index + 1);
        count--;
    }

    public void setSelected(int index, boolean selectionState) {
        if (index < 0 || index > count - 1) {
            throw new ArrayIndexOutOfBoundsException("Attempting to access shapesArray out of bounds.");
        }
        shapeArray[index].setSelected(selectionState);
        if (null != listener) {
            listener.shapesChanged();
        }
    }

    public void setSelected(final int[] fromArray, boolean selectionState) {
        for (int index : fromArray) {
            setSelected(index, selectionState);
        }
        if (null != listener) {
            listener.shapesChanged();
        }
    }

    public void select(Predicate<Shape> toCompare) {
        int index = 0;
        for (Shape shape : shapeArray) {
            if (shape == null) {
                break;
            }
            if (toCompare.test(shape)) {
                shapeArray[index].setSelected(true);
            }
            index++;
        }
    }

    public void removeSelected1() throws ArrayIndexOutOfBoundsException, InterruptedException {
        // 1st bad way, crashes since index out of bounds.
        int endIndex = count();
        for (int index = 0; index < endIndex; index++) {
            if (shapeArray[index].isSelected()) {
                remove(index);
            }
        }
    }

    public void removeSelected2() throws ArrayIndexOutOfBoundsException, InterruptedException {
        // 2nd bad (slow) way
        this.listener = null;
        for (int index = 0; index < count; index++) {
            if (null != shapeArray[index] && shapeArray[index].isSelected()) {
                remove(index);
                index--;
            }
        }
    }

    public void removeSelected3() throws ArrayIndexOutOfBoundsException, InterruptedException {
        // 3rd works, but...
        for (int index = count - 1; index >= 0; index--) {
            if (shapeArray[index].isSelected()) {
                remove(index);
            }
        }
    }

    public void removeSelected4() {
        // 4th fast way
        // TODO: Implement method Algorithms.partitionByRule!
        int selectedIndex = Algorithms.partitionByRule(shapeArray, count, shape -> shape.isSelected());
        // Uncomment this and implement it below.
         removeFrom(selectedIndex);
    }

        // TODO: Implement this method!
    public void removeFrom(int index) {
         for(int i = index; i < count; i++){
            shapeArray[i] = null;

            }
            count = index;
         }

    public Shape getShape(final int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > count - 1) {
            throw new ArrayIndexOutOfBoundsException("Attempting to access shapesArray out of bounds.");
        }
        return shapeArray[index];
    }

    private void moveElementsStepDownFrom(final int index) throws InterruptedException {
        if (index == 0 || index >= shapeArray.length) {
            return;
        }
        if (listener != null) {
            // For animation, do this step by step in a thread, sleeping a while in between.            
            Thread thread = new Thread(() -> {
                int counter = index;
                try {
                    for (; counter < shapeArray.length; counter++) {
                        if (shapeArray[counter] == null) {
                            break;
                        }
                        shapeArray[counter - 1] = shapeArray[counter];
                        shapeArray[counter] = null;
                        if (null != listener) {
                            listener.shapesChanged();
                            Thread.sleep(750);
                        }
                    }
                } catch (Exception ex) {
                    listener.exceptionHappened(ex.getLocalizedMessage());
                }
            });
            thread.start();
        } else {
            int counter = index;
            for (; counter < shapeArray.length; counter++) {
                if (shapeArray[counter] == null) {
                    break;
                }
                shapeArray[counter - 1] = shapeArray[counter];
                shapeArray[counter] = null;
            }
        }
    }

    public void draw(Graphics graphics) {
        for (Shape shape : shapeArray) {
            if (null == shape) {
                break;
            }
            shape.draw(graphics);
        }
    }

    private void reallocate() throws NegativeArraySizeException, NullPointerException {
        int newCapacity = capacity * 2;
        Shape[] newArray = Arrays.copyOf(shapeArray, newCapacity);
        this.capacity = newCapacity;
        this.shapeArray = newArray;
    }

    public void checkIntegrity() throws ShapeException {
        int index = 0;
        for (; index < count; index++) {
            if (shapeArray[index] == null) {
                throw new ShapeException("Shapes must not contain null shapes in 0..<count");
            }
        }
        for (; index < shapeArray.length; index++) {
            if (shapeArray[index] != null) {
                throw new ShapeException("Shapes must contain only null shapes in count..<length");
            }
        }
    }

}

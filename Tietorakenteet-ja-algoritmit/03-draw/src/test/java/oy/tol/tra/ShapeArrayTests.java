package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class ShapeArrayTests 
{
    private Shapes shapes = null;
    private int shapeCount = 0;
    private Shape shape = null;
    private Random generator = new Random();

    /**
     * Tests empty Shapes collection.
     */
    @Test
    public void emptyShapeArrayTest()
    {
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> { shapes.remove(0); }, "Should throw with empty shapes.");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> { shapes.remove(5); }, "Should throw with empty shapes.");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> { shapes.remove(-1); }, "Should throw with invalid index.");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> { shapes.getShape(0); }, "Should throw with empty shapes.");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> { shapes.getShape(5); }, "Should throw with empty shapes.");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> { shapes.getShape(-1); }, "Should throw with invalid index.");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertDoesNotThrow(() -> { shapes = new Shapes(10); }, "Constructing Shapes failed");
        assertThrows(ShapeException.class, () -> { shapes = new Shapes(1); }, "Shapes size must be >= 2");
        assertThrows(ShapeException.class, () -> { shapes = new Shapes(-1); }, "Shapes size must be >= 2");
    }

    @Test 
    public void basicShapesFillTest() {
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        shapeCount = 10;
        while (shapeCount-- >= 1) {
            assertDoesNotThrow(() -> { shapes.add(ShapeFactory.createRandomShape(1, 1)); }, "");
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(10, shapes.count(), "Shapes must have count of " + shapeCount);
        shapeCount = 20;
        while (shapeCount-- >= 1) {
            assertDoesNotThrow(() -> { shapes.add(ShapeFactory.createRandomShape(1, 1)); }, "");
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(30, shapes.count(), "Shapes must have count of " + shapeCount);
        shapeCount = shapes.count() - 1;
        while (shapeCount-- > 0) {
            assertDoesNotThrow(() -> { shape = shapes.getShape(shapeCount); }, "Must return shape when should have it.");
            assertNotNull(shape, () -> "Shapes must not contain null shapes.");
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        shapeCount = shapes.count();
        while (shapeCount > 0) {
            assertDoesNotThrow(() -> { shapes.remove(shapeCount - 1); }, "Must remove shape when should have some.");
            shapeCount--;
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Shapes must have count of " + shapeCount);
        shapeCount = 20;
        while (shapeCount-- >= 1) {
            assertDoesNotThrow(() -> { shapes.add(ShapeFactory.createRandomShape(1, 1)); }, "");
        }
        shapeCount = shapes.count();
        while (shapeCount > 0) {
            assertDoesNotThrow(() -> { shapes.remove(0); }, "Must return shape when should have it.");
            shapeCount--;
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Shapes must have count of " + shapeCount);
    }

    @Test 
    public void removeSelectionTest() {
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        shapeCount = 10;
        while (shapeCount-- >= 1) {
            assertDoesNotThrow(() -> { shapes.add(ShapeFactory.createRandomShape(1, 1)); }, "");
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(10, shapes.count(), "Shapes must have count of " + shapeCount);
        assertDoesNotThrow(() -> { shapes.setSelected(5, true); }, "Selecting a shape failed");
        assertDoesNotThrow(() -> { shapes.setSelected(7, true); }, "Selecting a shape failed");
        assertDoesNotThrow(() -> { shapes.removeSelected3(); }, "Removing shapes failed");
        assertEquals(8, shapes.count(), "Shapes must have count of " + shapeCount);
    }

    @Test 
    public void removeSelectionArrayTestWithMethod3() {
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        shapeCount = 10;
        while (shapeCount-- >= 1) {
            assertDoesNotThrow(() -> { shapes.add(ShapeFactory.createRandomShape(1, 1)); }, "");
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(10, shapes.count(), "Shapes must have count of " + shapeCount);
        int [] selected = {5,7,9};
        assertDoesNotThrow(() -> { shapes.setSelected(selected, true); }, "Selecting array of shapes failed");
        assertDoesNotThrow(() -> { shapes.removeSelected3(); }, "Removing shapes failed");
        assertEquals(7, shapes.count(), "Shapes must have count of " + shapeCount);
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
    }

    @Test 
    public void removeSelectionArrayTestWithMethod4() {
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        shapeCount = 10;
        while (shapeCount-- >= 1) {
            assertDoesNotThrow(() -> { shapes.add(ShapeFactory.createRandomShape(1, 1)); }, "");
        }
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(10, shapes.count(), "Shapes must have count of " + shapeCount);
        int [] selected = {5,7,9};
        assertDoesNotThrow(() -> { shapes.setSelected(selected, true); }, "Selecting array of shapes failed");
        assertDoesNotThrow(() -> { shapes.removeSelected4(); }, "Removing shapes failed");
        assertEquals(7, shapes.count(), "Shapes must have count of " + shapeCount);
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
    }

    @Test
    public void performanceTestWithMethod3() {
        System.out.println("****==-- Method 3 --==****");
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        final int SHAPE_COUNT = 100000;
        shapeCount = SHAPE_COUNT;
        long start = System.currentTimeMillis();
        try {
            while (shapeCount-- >= 1) {
                shapes.add(ShapeFactory.createRandomShape(1, 1));
            }                
        } catch (Exception e) {
            System.out.println("This should not happen in performance measurements, test Shapes better first");
        }
        System.out.println("Adding " + SHAPE_COUNT + " shapes took " + (System.currentTimeMillis() - start) + " ms");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(SHAPE_COUNT, shapes.count(), "Shapes must have count of " + SHAPE_COUNT);
        final int TO_REMOVE = 50000;
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < TO_REMOVE) {
            numbers.add(generator.nextInt(SHAPE_COUNT));
        }
        Integer[] indexesToRemove = new Integer[numbers.size()];
        indexesToRemove = numbers.toArray(indexesToRemove);
        for (int index = 0; index < TO_REMOVE; index++) {
            Arrays.sort(indexesToRemove);
        }
        start = System.currentTimeMillis();
        int[] intArray = Arrays.stream(indexesToRemove).mapToInt(Integer::intValue).toArray();
        shapes.setSelected(intArray, true);
        System.out.println("Marking " +  indexesToRemove.length + " shapes as selected took " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        assertDoesNotThrow(() -> { shapes.removeSelected3(); }, "Removing with method 3 should not throw");
        System.out.println("Removing " +  indexesToRemove.length + " selected shapes took " + (System.currentTimeMillis() - start) + " ms");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(SHAPE_COUNT-indexesToRemove.length, shapes.count(), "Shapes must have count of " + (SHAPE_COUNT-indexesToRemove.length));
    }

    @Test
    public void performanceTestWithMethod4() {
        System.out.println("****==-- Method 4 -=- YOUR STUFF HERE --==****");
        assertDoesNotThrow(() -> { shapes = new Shapes(); }, "Constructing Shapes failed");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(0, shapes.count(), "Empty shapes must have count of 0");
        final int SHAPE_COUNT = 100000;
        shapeCount = SHAPE_COUNT;
        long start = System.currentTimeMillis();
        try {
            while (shapeCount-- >= 1) {
                shapes.add(ShapeFactory.createRandomShape(1, 1));
            }                
        } catch (Exception e) {
            System.out.println("This should not happen in performance measurements, test Shapes better first");
        }
        System.out.println("Adding " + SHAPE_COUNT + " shapes took " + (System.currentTimeMillis() - start) + " ms");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(SHAPE_COUNT, shapes.count(), "Shapes must have count of " + SHAPE_COUNT);
        final int TO_REMOVE = 50000;
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < TO_REMOVE) {
            numbers.add(generator.nextInt(SHAPE_COUNT));
        }
        Integer[] indexesToRemove = new Integer[numbers.size()];
        indexesToRemove = numbers.toArray(indexesToRemove);
        for (int index = 0; index < TO_REMOVE; index++) {
            Arrays.sort(indexesToRemove);
        }
        start = System.currentTimeMillis();
        int[] intArray = Arrays.stream(indexesToRemove).mapToInt(Integer::intValue).toArray();
        shapes.setSelected(intArray, true);
        System.out.println("Marking " +  indexesToRemove.length + " shapes as selected took " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        shapes.removeSelected4();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Removing " +  indexesToRemove.length + " selected shapes took " + duration + " ms");
        assertDoesNotThrow(() -> { shapes.checkIntegrity();}, "Shapes integrity test failed");
        assertEquals(SHAPE_COUNT-indexesToRemove.length, shapes.count(), "Shapes must have count of " + (SHAPE_COUNT-indexesToRemove.length));
        if (duration > 100) {
            System.out.println("PROBLEM WITH SPEED, YOU SHOULD MAKE IT BETTER!!");
        }
    }

}

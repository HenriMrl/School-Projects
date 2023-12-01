package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import oy.tol.tra.Algorithms.ModeSearchResult;

@DisplayName("Basic tests for the Mode class.")
public class BasicTests {
    static final int MIN_ARRAY_SIZE = 500;
    static final int ARRAY_SIZE_INCREMENT = 500000;
    static final int MEASUREMENTS = 7;
    static final int MIN_VALUE = 1;

    @Test 
    @DisplayName("Executing Basic tests")
    void basicTests() {
        System.out.println("Testing basic functionality of implementation with small arrays.");
        try {
            // Test null array
            Integer [] array = null;
            ModeSearchResult<Integer> result = Algorithms.findMode(array);
            assertNull(result.theMode, () -> "When array is null, result.theMode must be null too.");
            assertEquals(-1, result.count, () -> "When array is null, result.count must be -1");
            // Test array that cannot have a mode
            array = new Integer[1];
            array[0] = 1;
            result = Algorithms.findMode(array);
            assertNull(result.theMode, () -> "When array has only one element, result.theMode must be null too.");
            assertEquals(-1, result.count, () -> "When array has only one element, result.count must be -1");
            // Test array that has two elements, both the same value.
            array = new Integer[2];
            array[0] = 1;
            array[1] = 1;
            result = Algorithms.findMode(array);
            assertEquals(1, result.theMode, () -> "Array with two same numbers must return that number as mode");
            assertEquals(2, result.count, () -> "When array has two same elements, result.count must be 2");
            // Test an array with four elements, mode will be in the beginning.
            array = new Integer[4];
            array[0] = 2;
            array[1] = 1;
            array[2] = 3;
            array[3] = 1;
            result = Algorithms.findMode(array);
            assertEquals(1, result.theMode, () -> "Array with two same numbers must return that number as mode");
            assertEquals(2, result.count, () -> "When array has two same elements, result.count must be 2");
            // Test an array with four elements, mode will be in the middle.
            array = new Integer[4];
            array[0] = 2;
            array[1] = 3;
            array[2] = 2;
            array[3] = 1;
            result = Algorithms.findMode(array);
            assertEquals(2, result.theMode, () -> "Array with two same numbers must return that number as mode");
            assertEquals(2, result.count, () -> "When array has two same elements, result.count must be 2");
            // Test an array with four elements, mode will be in the end.
            array = new Integer[4];
            array[0] = 1;
            array[1] = 3;
            array[2] = 3;
            array[3] = 2;
            result = Algorithms.findMode(array);
            assertEquals(3, result.theMode, () -> "Array with two same numbers must return that number as mode");
            assertEquals(2, result.count, () -> "When array has two same elements, result.count must be 2");
        } catch (Exception e) {
            fail("findMode must not throw!");
        }
    }

}

package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

@DisplayName("Basic tests for the Mode class.")
public class ModeTests {
    static final int MIN_ARRAY_SIZE = 1000;
    static final int ARRAY_SIZE_INCREMENT = 1000;
    static final int MEASUREMENTS = 25;
    static final int MIN_VALUE = 1;

    @Test
    @DisplayName("Tests the Mode.findMode() implementation")
    void findModeTests() {
        try {
            System.out.println("Testing findMode with Doubles");
            ThreadLocalRandom tlr = ThreadLocalRandom.current();
            int arraySize = MIN_ARRAY_SIZE;
            int measurementCounter = 0;
            System.out.format("%10s\t%10s\t%s%n", "Array size (n)", "time (ms)", "Mode value found");
            while (measurementCounter < MEASUREMENTS) {
                double preferredNumber = tlr.nextInt(MIN_VALUE, arraySize);
                int preferredNumberCount = arraySize / 3;
                Double [] array = generateArray(arraySize, MIN_VALUE, preferredNumber, preferredNumberCount);
                long start = System.currentTimeMillis();
                Algorithms.ModeSearchResult<Double> foundMode = Algorithms.findMode(array);
                long duration = System.currentTimeMillis() - start;
                System.out.format("%10d\t%10d\t%.2f%n", 
                                  arraySize, 
                                  duration,
                                  foundMode.theMode);
                assertEquals(preferredNumber, foundMode.theMode);
                assertEquals(preferredNumberCount, foundMode.count);
                arraySize += ARRAY_SIZE_INCREMENT;
                // arraySize *= 2;
                measurementCounter++;
            }
            System.out.println("Done!");
        } catch (Exception e) {
            fail("Something went wrong in finding the mode." + e.getMessage());
        }

    }
    
    /**
     * Generates an array with specified size and fills it with random numbers from
     * the range minValue..<maxValue. Creates more numbers with preferred value.
     * 
     * @param size            The size of the array.
     * @param minValue        Minimum value put in the array.
     * @param maxValue        Maximum value (exclusive) to put in the array.
     * @param preferredNumber Number preferred (more of this value is put into the
     *                        array)
     */
    private Double[] generateArray(int size, double minValue, double preferredNumber, int preferredNumberCount) {
        // Students, DO NOT touch this method!
        Double [] array = new Double[size];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int counter = 0; counter < size; counter++) {
            double number;
            do {
                number = tlr.nextDouble(minValue, size * 2);
            } while (number == preferredNumber);
            array[counter] = number;
        }
        for (int counter = 0; counter < preferredNumberCount; counter++) {
            int index = tlr.nextInt(0, size);
            while (array[index] == preferredNumber) {
                index = tlr.nextInt(0, size);
            }
            array[index] = preferredNumber;
        }
        return array;
    }

}

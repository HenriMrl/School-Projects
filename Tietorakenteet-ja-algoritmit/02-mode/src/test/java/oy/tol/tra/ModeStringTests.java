package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

@DisplayName("String tests for the Mode class.")
public class ModeStringTests {
    static final int MIN_ARRAY_SIZE = 1000;
    static final int ARRAY_SIZE_INCREMENT = 1000;
    static final int MEASUREMENTS = 25;
    static final Random random = new Random();

    @Test
    @DisplayName("Tests the Mode.findMode() implementation")
    void findModeTests() {
        try {
            System.out.println("Testing findMode with Strings");
            int arraySize = MIN_ARRAY_SIZE;
            int measurementCounter = 0;
            System.out.format("%10s\t%10s\t%s%n", "Array size (n)", "time (ms)", "Mode value found");
            while (measurementCounter < MEASUREMENTS) {
                final String preferredString = "tira-mode";
                int preferredStringCount = arraySize / 3;
                String [] array = generateArray(arraySize, preferredString, preferredStringCount);
                long start = System.currentTimeMillis();
                Algorithms.ModeSearchResult<String> foundMode = Algorithms.findMode(array);
                long duration = System.currentTimeMillis() - start;
                System.out.format("%10d\t%10d\t%s%n", 
                                  arraySize, 
                                  duration,
                                  foundMode.theMode);
                assertEquals(0, preferredString.compareTo(foundMode.theMode), () -> "The found string was not the most frequent one!");
                assertEquals(preferredStringCount, foundMode.count, () -> "The count of the most frequent item did not match what was expected.");
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
    private String [] generateArray(int size, String preferredString, int preferredStringCount) {
        // Students, DO NOT touch this method!
        String [] array = new String[size];
        for (int counter = 0; counter < size; counter++) {
            array[counter] = randomString();
        }
        for (int counter = 0; counter < preferredStringCount; counter++) {
            int index = random.nextInt(0, size);
            while (array[index].equals(preferredString)) {
                index = random.nextInt(0, size);
            }
            array[index] = preferredString;
        }
        return array;
    }

    private String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
    
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    
        return generatedString;
    }
}

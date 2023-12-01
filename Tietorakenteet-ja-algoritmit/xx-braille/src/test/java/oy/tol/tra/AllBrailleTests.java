package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestClassOrder;

@DisplayName("Tests converting plain text to Braille symbols.")
public class AllBrailleTests {

    static long kalevalaSlowDuration = 0;
    static long bulkSlowDuration = 0;
    static long kalevalaHashtableDuration = 0;
    static long bulkHashtableDuration = 0;
 
    @RepeatedTest(10)
    @Order(1)
    void testKalevala() {
        try {
            System.out.println("Testing slow Braille converter with Kalevala... ");
            BrailleConverter converter = new BrailleConverter();
            long start = System.currentTimeMillis();
            assertDoesNotThrow(
                    () -> converter.initialize(BrailleConverter.Converter.SLOW,
                            getFullPathToTestFile("symboltable.tsv")),
                    () -> "Initializing the converter must not throw");
            boolean result = converter.convertTextFileToBraille(getFullPathToTestFile("Kalevala.txt"),
                    "Kalevala-Braille.txt");
            kalevalaSlowDuration += System.currentTimeMillis() - start;
            assertTrue(result, () -> "Failed to convert file for some reason.");
        } catch (Exception e) {
            fail("Could not read or write test data files");
        }
    }

    @RepeatedTest(10)
    @Order(2)
    void testBulk() {
        try {
            System.out.println("Testing slow Braille converter with Bulk.txt... ");
            BrailleConverter converter = new BrailleConverter();
            long start = System.currentTimeMillis();
            assertDoesNotThrow(
                    () -> converter.initialize(BrailleConverter.Converter.SLOW,
                            getFullPathToTestFile("symboltable.tsv")),
                    () -> "Initializing the converter must not throw");
            boolean result = converter.convertTextFileToBraille(getFullPathToTestFile("Bulk.txt"),
                    "Bulk-Braille.txt");
            bulkSlowDuration += System.currentTimeMillis() - start;
            assertTrue(result, () -> "Failed to convert file for some reason.");
        } catch (Exception e) {
            fail("Could not read or write test data files");
        }
    }


    @RepeatedTest(10)
    @Order(3)
    void testKalevalaWithHashTable() {
        try {
            System.out.println("Testing hashtable Braille converter with Kalevala... ");
            BrailleConverter converter = new BrailleConverter();
            long start = System.currentTimeMillis();
            assertDoesNotThrow(
                    () -> converter.initialize(BrailleConverter.Converter.HASHTABLE,
                            getFullPathToTestFile("symboltable.tsv")),
                    () -> "Initializing the converter must not throw");
            boolean result = converter.convertTextFileToBraille(getFullPathToTestFile("Kalevala.txt"),
                    "Kalevala-Braille.txt");
            kalevalaHashtableDuration += System.currentTimeMillis() - start;
            assertTrue(result, () -> "Failed to convert file for some reason.");
        } catch (Exception e) {
            fail("Could not read or write test data files");
        }
    }

    @RepeatedTest(10)
    @Order(4)
    void testBulkWithHashTable() {
        try {
            System.out.println("Testing hashtable Braille converter with Bulk.txt... ");
            BrailleConverter converter = new BrailleConverter();
            long start = System.currentTimeMillis();
            assertDoesNotThrow(
                    () -> converter.initialize(BrailleConverter.Converter.HASHTABLE,
                            getFullPathToTestFile("symboltable.tsv")),
                    () -> "Initializing the converter must not throw");
            boolean result = converter.convertTextFileToBraille(getFullPathToTestFile("Bulk.txt"),
                    "Bulk-Braille.txt");
            bulkHashtableDuration += System.currentTimeMillis() - start;
            assertTrue(result, () -> "Failed to convert file for some reason.");
        } catch (Exception e) {
            fail("Could not read or write test data files");
        }
    }

    @AfterAll
    static void printStatistics() {
        assertNotEquals(0, kalevalaSlowDuration,
                () -> "Run all the test always to get comparison results!");
        assertNotEquals(0, bulkSlowDuration,
                () -> "Run all the test always to get comparison results!");
        System.out.println("\n               ========== Statistics ==========\n");
        System.out.format(" Converting Kalevala.txt with slow table 10 times took %6d ms%n", kalevalaSlowDuration);
        System.out.format(" Converting Kalevala.txt with hash table 10 times took %6d ms%n", kalevalaHashtableDuration);
        System.out.format(" Converting Bulk.txt     with slow table 10 times took %6d ms%n", bulkSlowDuration);
        System.out.format(" Converting Bulk.txt     with hash table 10 times took %6d ms%n", bulkHashtableDuration);
        double percentOfLinear = ((double) bulkHashtableDuration
                / (double) bulkSlowDuration) * 100.0;
        System.out.format(" >> Hashtable execution time was %.2f%% of slow table time%n", percentOfLinear);
        assertTrue(percentOfLinear <= 75.0,
                () -> "Hash table implementation with Bulk.txt should execute in less than 75% time of linear.");
    }

    private String getFullPathToTestFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }
}

package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

@DisplayName("Tests converting plain text to Braille symbols.")
public class SlowBrailleTests {

    static long kalevalaSlowDuration = 0;
    static long bulkSlowDuration = 0;
    static long kalevalaHashtableDuration = 0;
    static long bulkHashtableDuration = 0;
 
    @Order(1)
    @RepeatedTest(10)
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

    @AfterAll
    static void printStatistics() {
        System.out.println("\n               ========== Statistics ==========\n");
        System.out.println(" Converting Kalevala.txt with slow table 10 times took "
                + kalevalaSlowDuration + " ms");
        System.out.println(
                " Converting Bulk.txt with slow table 10 times took " + bulkSlowDuration + " ms");
    }

    private String getFullPathToTestFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }

}

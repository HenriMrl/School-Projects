package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests converting plain text to Braille symbols.")
public class HashCorrectnessTests {
 
    private static final String expectedOutput = "⠠⠧⠑⠗⠽⠀⠎⠊⠍⠏⠇⠑⠀⠋⠊⠇⠑⠀⠺⠊⠞⠓⠀⠁⠀⠝⠥⠍⠃⠑⠗⠀⠼⠙⠃⠀⠞⠕⠀⠞⠑⠎⠞⠀⠉⠕⠝⠧⠑⠗⠎⠊⠕⠝⠄\n\n⠠⠃⠑⠎⠞⠀⠗⠑⠛⠁⠗⠙⠎⠂⠀⠠⠁⠝⠞⠞⠊\n";
    
    private static String outputString = null;

    @Test
    void testSimpleFile() {
        try {
            System.out.println("Testing slow Braille converter with simple.txt... ");
            BrailleConverter converter = new BrailleConverter();
            assertDoesNotThrow(
                    () -> converter.initialize(BrailleConverter.Converter.HASHTABLE,
                            getFullPathToTestFile("symboltable.tsv")),
                    () -> "Initializing the converter must not throw");
            boolean result = converter.convertTextFileToBraille(getFullPathToTestFile("simple.txt"),
                    "simple-Braille.txt");
            assertTrue(result, () -> "Failed to convert file for some reason.");
            assertDoesNotThrow(() -> outputString = readOutputFileContents(), () -> "Reading output file should not throw");
            assertNotNull(outputString, () -> "Reading output file returned null");
            System.out.println(expectedOutput);
            System.out.println(outputString);
            assertEquals(expectedOutput, outputString, () -> "Output file must match expected output string.");
        } catch (Exception e) {
            fail("Could not read or write test data files");
        }
    }

    private String getFullPathToTestFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }

    private String readOutputFileContents() throws IOException {
        Path fileName = Path.of("simple-Braille.txt");
        return Files.readString(fileName);
    }
}

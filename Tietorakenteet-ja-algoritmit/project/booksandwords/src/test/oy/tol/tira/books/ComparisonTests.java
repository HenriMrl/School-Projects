package oy.tol.tira.books;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//TODO: TEACHER do not give this test to students.

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Tests using different Book implementations")
public class ComparisonTests {
   
   private static long baseLineTime = -1;
   private static final String TESTFILE = "Bulk.txt";
   private static final String IGNOREFILE = "ignore-words.txt";

   private static final String outputFileName = "impl-compare.csv";
   private static final String separator = ",";
   private static BufferedWriter writer = null;

   @BeforeAll
   static void openOutputFile() {
       try {
           writer = new BufferedWriter(new FileWriter(outputFileName));
           writer.append("Implementation"+separator+"Time (ms)");
           writer.newLine();
       } catch (IOException e) {
           fail("Could not open test output file for writing");
       }
   }


   // @Test
   // @Order(1)
   public void baseLineTest() {
      System.out.println("Starting to test with BadBookImplementation...");
      Book testTarget = new BadBookImplementation();
      String testBook = getFullPathToTestFile(TESTFILE);
      String ignoreFile = getFullPathToTestFile(IGNOREFILE);
      long start = System.currentTimeMillis();
      assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
      assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
      assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
      long end = System.currentTimeMillis();
      baseLineTime = end - start;
      try {
         writer.append("BadBookImplementation");
         writer.append(separator);
         writer.append(Long.toString(baseLineTime));
         writer.newLine();
      } catch (IOException e) {
         System.out.println("Failed to write results to csv file");
      }
   }

   @Test
   @Order(2)
   public void betterImplementationTest() {
      System.out.println("Starting to test with BetterBookImplementation...");
      Book testTarget = new BetterBookImplementation();
      String testBook = getFullPathToTestFile(TESTFILE);
      String ignoreFile = getFullPathToTestFile(IGNOREFILE);
      long start = System.currentTimeMillis();
      assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
      assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
      assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
      long end = System.currentTimeMillis();
      long betterTime = end - start;
      if (baseLineTime > 0) {
         long relativeSpeed = baseLineTime / betterTime;
         System.out.println("BetterBookImplementation was " + relativeSpeed + " times faster than trivial bad implementation.");
      } else {
         System.out.println("Baseline test not executed or result was invalid.");
      }
      try {
         writer.append("BetterBookImplementation (Java classes)");
         writer.append(separator);
         writer.append(Long.toString(betterTime));
         writer.newLine();
      } catch (IOException e) {
         System.out.println("Failed to write results to csv file");
      }
   }

   @Test
   @Order(3)
   public void myImplementationTest() {
      System.out.println("Starting to test MyBookImplementation...");
      Book testTarget = new MyBookImplementation();
      String testBook = getFullPathToTestFile(TESTFILE);
      String ignoreFile = getFullPathToTestFile(IGNOREFILE);
      long start = System.currentTimeMillis();
      assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
      assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
      assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
      long end = System.currentTimeMillis();
      long betterTime = end - start;
      if (baseLineTime > 0) {
         long relativeSpeed = baseLineTime / betterTime;
         System.out.println("BetterBookImplementation was " + relativeSpeed + " times faster than trivial bad implementation.");
      } else {
         System.out.println("Baseline test not executed or result was invalid.");
      }
      try {
         writer.append("MyBookImplementation (Hash table + ignore table)");
         writer.append(separator);
         writer.append(Long.toString(betterTime));
         writer.newLine();
      } catch (IOException e) {
         System.out.println("Failed to write results to csv file");
      }
   }

   @Test
   @Order(4)
   public void myHashBookImplementationTest() {
      System.out.println("Starting to test HashBookImplementation2...");
      Book testTarget = new HashBookImplementation2();
      String testBook = getFullPathToTestFile(TESTFILE);
      String ignoreFile = getFullPathToTestFile(IGNOREFILE);
      long start = System.currentTimeMillis();
      assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
      assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
      assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
      long end = System.currentTimeMillis();
      long betterTime = end - start;
      if (baseLineTime > 0) {
         long relativeSpeed = baseLineTime / betterTime;
         System.out.println("BetterBookImplementation was " + relativeSpeed + " times faster than trivial bad implementation.");
      } else {
         System.out.println("Baseline test not executed or result was invalid.");
      }
      try {
         writer.append("HashBookImplementation2 (one hash table)");
         writer.append(separator);
         writer.append(Long.toString(betterTime));
         writer.newLine();
      } catch (IOException e) {
         System.out.println("Failed to write results to csv file");
      }
   }
   @Test
   @Order(5)
   public void myBinaryTreeImplementationTest() {
      System.out.println("Starting to test MyBinaryTreeImplementation..");
      Book testTarget = new MyBTImplementation();
      String testBook = getFullPathToTestFile(TESTFILE);
      String ignoreFile = getFullPathToTestFile(IGNOREFILE);
      long start = System.currentTimeMillis();
      assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
      assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
      assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
      long end = System.currentTimeMillis();
      long betterTime = end - start;
      if (baseLineTime > 0) {
         long relativeSpeed = baseLineTime / betterTime;
         System.out.println("MyBinaryTreeImplementation was " + relativeSpeed + " times faster than trivial bad implementation.");
      } else {
         System.out.println("Baseline test not executed or result was invalid.");
      }
      try {
         writer.append("MyBinaryTreeImplementation");
         writer.append(separator);
         writer.append(Long.toString(betterTime));
         writer.newLine();
      } catch (IOException e) {
         System.out.println("Failed to write results to csv file");
      }
   }

   @Test
   @Order(6)
   public void myParallelImplementationTest() {
      System.out.println("Starting to test ParallelBookImplementation..");
      Book testTarget = new ParallelBookImplementation();
      String testBook = getFullPathToTestFile(TESTFILE);
      String ignoreFile = getFullPathToTestFile(IGNOREFILE);
      long start = System.currentTimeMillis();
      assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
      assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
      assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
      long end = System.currentTimeMillis();
      long betterTime = end - start;
      if (baseLineTime > 0) {
         long relativeSpeed = baseLineTime / betterTime;
         System.out.println("ParallelBookImplementation was " + relativeSpeed + " times faster than trivial bad implementation.");
      } else {
         System.out.println("Baseline test not executed or result was invalid.");
      }
      try {
         writer.append("ParallelBookImplementation");
         writer.append(separator);
         writer.append(Long.toString(betterTime));
         writer.newLine();
      } catch (IOException e) {
         System.out.println("Failed to write results to csv file");
      }
   }


   @AfterAll
   static void closeOutputFile() {
       try {
           writer.close();
       } catch (IOException e) {
           fail("Could not close test output file");
       }
   }

   private String getFullPathToTestFile(String fileName) {
      ClassLoader classLoader = getClass().getClassLoader();
      File file = new File(classLoader.getResource(fileName).getFile());
      return file.getAbsolutePath();
  }

}

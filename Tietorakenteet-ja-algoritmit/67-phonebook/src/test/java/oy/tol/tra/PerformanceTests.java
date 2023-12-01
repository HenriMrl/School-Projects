package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Performance yests using different implementations and file sizes")
public class PerformanceTests {

	private static final String outputFileName = "compare.csv";
	private static final String separator = ",";
	private static int currentIndex = 0;
	private static BufferedWriter writer = null;
	Integer[] population = { 100, 1000, 10000, 50000, 100000, 500000, 1000000, 2000000,  5000000 };
	private static final String[] testFiles = { "village.txt", "small-town.txt", "town.txt", "large-town.txt",
			"city.txt", "large-city.txt", "metropolis.txt", "capital.txt", "megalopolis.txt" };

	@BeforeAll
	static void openOutputFile() {
		try {
			writer = new BufferedWriter(new FileWriter(outputFileName));
			writer.append("testfile,bytes,elements,time (ms)");
			writer.newLine();
		} catch (IOException e) {
			fail("Could not open test output file for writing");
		}
	}

	@Test
	void handleReadTestFilesWithBST() {
		if (null != writer) {
			try {
				currentIndex = 0;
				writer.append("Binary search tree:");
				writer.newLine();
				while (currentIndex < testFiles.length) {
					System.out.println("==> Starting to analyse BST phonebook " + testFiles[currentIndex]);
					// String path = getFullPathToTestFile(testFiles[currentIndex]);
					File file = new File(testFiles[currentIndex]);
					long fileSize = file.length();
					file = null;
					Dictionary<Person, PhoneNumber> fastBST = new KeyValueBSearchTree<>();
					long start = System.currentTimeMillis();
					readPersonsFromFile(testFiles[currentIndex], fastBST);
					long end = System.currentTimeMillis();
					assertEquals(population[currentIndex], fastBST.size());
					long duration = end - start;
					/*
					 * testfile,bytes,elements,duration
					 */
					writer.append(testFiles[currentIndex]);
					writer.append(separator);
					writer.append(Long.toString(fileSize));
					writer.append(separator);
					writer.append(Long.toString(fastBST.size()));
					writer.append(separator);
					writer.append(Long.toString(duration));
					writer.newLine();
					writer.flush();
					System.out.println(fastBST.getStatus());
					fastBST = null;
					currentIndex++;
				}
			} catch (Exception e) {
				fail("Could not write test output file: " + e.getMessage());
			}
		} else {
			fail("Cannot run tests since opening output file writer failed.");
		}
	}

	@Test
	void handleReadTestFilesWithHashTable() {
		if (null != writer) {
			try {
				currentIndex = 0;
				writer.append("Hashtable:");
				writer.newLine();
				while (currentIndex < testFiles.length) {
					System.out.println("==> Starting to analyse Hashtable phonebook " + testFiles[currentIndex]);
					File file = new File(testFiles[currentIndex]);
					long fileSize = file.length();
					file = null;
					Dictionary<Person, PhoneNumber> fastHashTable = new KeyValueHashTable<>();
					long start = System.currentTimeMillis();
					readPersonsFromFile(testFiles[currentIndex], fastHashTable);
					long end = System.currentTimeMillis();
					assertEquals(population[currentIndex], fastHashTable.size());
					long duration = end - start;
					/*
					 * testfile,bytes,elements,duration
					 */
					writer.append(testFiles[currentIndex]);
					writer.append(separator);
					writer.append(Long.toString(fileSize));
					writer.append(separator);
					writer.append(Long.toString(fastHashTable.size()));
					writer.append(separator);
					writer.append(Long.toString(duration));
					writer.newLine();
					writer.flush();
					System.out.println(fastHashTable.getStatus());
					fastHashTable = null;
					currentIndex++;
				}
			} catch (Exception e) {
				fail("Could not write test output file: " + e.getMessage());
			}
		} else {
			fail("Cannot run tests since opening output file writer failed.");
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

	static private void readPersonsFromFile(String fileName, Dictionary<Person, PhoneNumber> toPhoneBook) throws IOException {
		BufferedReader phoneBookReader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
		String line;
		while ((line = phoneBookReader.readLine()) != null && line.length() > 0) {
			 String personElements[] = line.split(",");
			 if (personElements.length == 5) {
				  Person person = new Person(personElements[0], personElements[1]);
				  PhoneNumber number = new PhoneNumber(personElements[2], personElements[3], personElements[4]);
				  toPhoneBook.add(person, number);
			 }
		}
		phoneBookReader.close();
  }
}

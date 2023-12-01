package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Basic tests for the Phone book.")
public class PhoneBookTests {

    // Implementations to test:
    static Dictionary<Person, PhoneNumber> slowPhoneBook = null;
    static Dictionary<Person, PhoneNumber> fastHashTablePhoneBook = null;
    static Dictionary<Person, PhoneNumber> fastBSTPhoneBook = null;
    static int entryCount = 0;

    // Test classes
    static Map<Person, PhoneNumber> testPersons = new HashMap<>();
    static Person[] persons = null; 

    static boolean didRunHashTableBookTests = false;
    static boolean didRunBSTBookTests = false;
    static long readingToSlowPhoneBook = 0;
    static long readingToHashTablePhoneBook = 0;
    static long readingToBSTPhoneBook = 0;
    static long linearSearchTime = 0;
    static long sortTime = 0;
    static long fastHashTableSearchTime = 0;
    static long fastBSTSearchTime = 0;

    @BeforeAll
    static void readFromFile() {
        try {
            System.out.println("Reading test data from file, please wait...");
            didRunHashTableBookTests = false;
            slowPhoneBook = new KeyValueArray<>();
            fastHashTablePhoneBook = new KeyValueHashTable<>(25);
            fastBSTPhoneBook = new KeyValueBSearchTree<>();
            long start = System.nanoTime();
            readPersonsFromFile(slowPhoneBook);
            entryCount = slowPhoneBook.size();
            readingToSlowPhoneBook = System.nanoTime() - start;
            if (fastHashTablePhoneBook.getType() == Dictionary.Type.HASHTABLE) {
                start = System.nanoTime();
                readPersonsFromFile(fastHashTablePhoneBook);
                readingToHashTablePhoneBook = System.nanoTime() - start;    
                assertEquals(entryCount, fastHashTablePhoneBook.size(), "All persons were not added to the phonebook");
            }
            if (fastBSTPhoneBook.getType() == Dictionary.Type.BST) {
                start = System.nanoTime();
                readPersonsFromFile(fastBSTPhoneBook);
                readingToBSTPhoneBook = System.nanoTime() - start;
                assertEquals(entryCount, fastBSTPhoneBook.size(), "All persons were not added to the phonebook");
            }
            testPersons.clear();
            testPersons.put(new Person("Akseli", "Gallen-Kallela"), new PhoneNumber("380", "020", "7059879"));
            testPersons.put(new Person("Eero","Järnefelt"), new PhoneNumber("212", "045", "7081397"));
            testPersons.put(new Person("Leonardo", "Da Vinci"), new PhoneNumber("212","040","7113582"));
            testPersons.put(new Person("Ellen", "Thesleff"), new PhoneNumber("1","042","7076522"));
            testPersons.put(new Person("Venny","Soldan-Brofeldt"), new PhoneNumber("212","020","7111270"));
            testPersons.put(new Person("Aleksandrs", "Picasso"), new PhoneNumber("358","040","7101119"));
            testPersons.put(new Person("Derick", "Koivu"), new PhoneNumber("1","020","7054832"));
        } catch (IOException e) {
            fail("Could not read test data from PhoneBook.txt");
        }
    }

    @AfterAll 
    static void printStatistics() {
        System.out.println("\n               ========== Statistics ==========\n");
        System.out.println(" ---=== Entry numbers in Dictionaries ===---\n");
        // First test entry size etc.
        System.out.format("%35s\t%10d%n", "Slow Phonebook entries:", slowPhoneBook.size());
        if (didRunHashTableBookTests) {
            System.out.format("%35s\t%10d%n", "Hash table Phonebook entries:", fastHashTablePhoneBook.size());
            assertEquals(slowPhoneBook.size(), fastHashTablePhoneBook.size(), () -> "Hash table size must equal to slow phonebook size");
        }
        if (didRunBSTBookTests) {
            System.out.format("%35s\t%10d%n", "BST Phonebook entries:", fastBSTPhoneBook.size());
            assertEquals(slowPhoneBook.size(), fastBSTPhoneBook.size(), () -> "BST size must equal to slow phonebook size");
        }
        // Then insertion time efficiency.
        System.out.println("\n ---=== Insertion speeds ===---\n");
        System.out.format("%45s\t%10d ns%n", "Inserting to slow phonebook took:", readingToSlowPhoneBook);
        if (didRunHashTableBookTests) {
            System.out.format("%45s\t%10d ns%n", "Inserting to hash table phonebook took:", readingToHashTablePhoneBook);
        }
        if (didRunBSTBookTests) {
            System.out.format("%45s\t%10d ns%n", "Inserting to BST phonebook took:", readingToBSTPhoneBook);
        }
        if (didRunHashTableBookTests) {
            double comparison = ((double)readingToHashTablePhoneBook / (double)readingToSlowPhoneBook);
            System.out.format("Inserting to hash table phonebook was %.2f times of slow phonebook.%n", comparison);
        }
        if (didRunBSTBookTests) {
            double comparison = ((double)readingToBSTPhoneBook / (double)readingToSlowPhoneBook);
            System.out.format("Inserting to BST phonebook was %.2f times of slow phonebook.%n", comparison);
        }
        if (didRunHashTableBookTests && didRunBSTBookTests) {
            double comparison = ((double)readingToHashTablePhoneBook / (double)readingToBSTPhoneBook);
            System.out.format("Inserting to hash table phonebook was %.2f times of BST phonebook.%n", comparison);
        }
        // Search time efficiency tests
        System.out.println("\n ---=== Search time efficiency tests ===---\n");
        System.out.format("%35s\t%10d ns%n", "Linear array search took: ", linearSearchTime);
        if (didRunHashTableBookTests) {
            System.out.format("%35s\t%10d ns%n", "Fast hash table search took: ", fastHashTableSearchTime);
            System.out.format("%35s\t%10d %s%n", "Fast hash table search was: ", linearSearchTime / fastHashTableSearchTime, " times faster than linear search");
            double comparison = ((double)linearSearchTime / (double)fastHashTableSearchTime);
            double lowerLimit = 100.0;
            assertTrue(comparison >= lowerLimit, () -> "Hash table search should be 100 times faster than linear search");
        }

        if (didRunBSTBookTests) {
            System.out.format("%35s\t%10d ns%n", "Fast BST search took: ", fastBSTSearchTime);
            System.out.format("%35s\t%10d %s%n", "Fast BST search was: ", linearSearchTime / fastBSTSearchTime, " times faster than linear search");
            double comparison = ((double)linearSearchTime / (double)fastBSTSearchTime);
            double lowerLimit = 100.0;
            assertTrue(comparison >= lowerLimit, () -> "BST search should be 100 times faster than linear search");
        }
        if (didRunHashTableBookTests && didRunBSTBookTests) {
            double comparison = ((double)fastHashTableSearchTime / (double)fastBSTSearchTime);
            System.out.format("Searching from hash table phonebook was %.2f times of BST phonebook search.%n", comparison);
        }

        System.out.println("\n ---=== Test reports from Dictionary<Person, PhoneNumber> implementations ===---\n");
        System.out.println(slowPhoneBook.getStatus());
        if (didRunHashTableBookTests) {
            System.out.println(fastHashTablePhoneBook.getStatus());
        }
        if (didRunBSTBookTests) {
            System.out.println(fastBSTPhoneBook.getStatus());
        }
        System.out.println("\n ---=== END OF TEST REPORT ===---\n");
    }

    @Test
    @DisplayName("Tests the slow phonebook")
    void slowPhoneBookTests() {
        try {
            System.out.println(">> Testing slow phonebook with " + slowPhoneBook.size() + " entries");
            for (Person testPerson : testPersons.keySet()) {
                long start = System.nanoTime();
                PhoneNumber found = slowPhoneBook.find(testPerson);
                linearSearchTime += System.nanoTime() - start;
                assertNotNull(found, () -> "Should find a number for the person.");
                assertEquals(testPersons.get(testPerson).getPhoneNumber(), found.getPhoneNumber(), () -> "Not the same number that was searched.");
                System.out.println("Linear search for person " + testPerson.getFullName() + " found number " + found.getPhoneNumber());
            }
        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    @DisplayName("Tests the searching of fast phonebook(s)")
    void fastPhoneBookTests() {
        try {
            if (fastHashTablePhoneBook.getType() == Dictionary.Type.HASHTABLE) {
                assertThrows(IllegalArgumentException.class, () -> fastHashTablePhoneBook.add(null, null), () -> "Must throw if parameters are null");
                assertThrows(OutOfMemoryError.class, () -> fastHashTablePhoneBook.ensureCapacity(Integer.MAX_VALUE));
                System.out.println(">> Testing hash table phonebook search with " + fastHashTablePhoneBook.size() + " entries");
                for (Person testPerson : testPersons.keySet()) {
                    long start = System.nanoTime();
                    PhoneNumber found = fastHashTablePhoneBook.find(testPerson);
                    fastHashTableSearchTime += System.nanoTime() - start;
                    assertNotNull(found, () -> "Should find that person's phone number.");
                    assertEquals(testPersons.get(testPerson).getPhoneNumber(), found.getPhoneNumber(), () -> "Not the same number that was searched.");
                    System.out.println("Searched for person " + testPerson.getFullName() + " and number " + found.getPhoneNumber());
                }
                didRunHashTableBookTests = true;    
            } else {
                System.out.println("Not testing hash table yet since it has not been implemented.");
            }
            if (fastBSTPhoneBook.getType() == Dictionary.Type.BST) {
                assertThrows(IllegalArgumentException.class, () -> fastBSTPhoneBook.add(null, null), () -> "Must throw if parameters are null");
                System.out.println(">> Testing BST phonebook with " + fastBSTPhoneBook.size() + " entries");
                for (Person testPerson : testPersons.keySet()) {
                    long start = System.nanoTime();
                    PhoneNumber found = fastBSTPhoneBook.find(testPerson);
                    fastBSTSearchTime += System.nanoTime() - start;
                    assertNotNull(found, () -> "Should find that person's phone number.");
                    assertEquals(testPersons.get(testPerson).getPhoneNumber(), found.getPhoneNumber(), () -> "Not the same number that was searched.");
                    System.out.println("Searched for person " + testPerson.getFullName() + " and number " + found.getPhoneNumber());
                }
                didRunBSTBookTests = true;    
            } else {
                System.out.println("Not testing BST yet since it has not been implemented.");
            }
        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    void searchPersonNotInPhoneBook() {
        System.out.println("Searching persons not in the phonebooks...");
        // First the slow  book
        assertNull(slowPhoneBook.find(new Person("Antti", "Juustila")), () -> "Should return null when person is not in the phonebook");
        assertNull(slowPhoneBook.find(new Person("Pertti", "Karhapää")), () -> "Should return null when person is not in the phonebook");
        assertNull(slowPhoneBook.find(new Person("Jouni", "Lappalainen")), () -> "Should return null when person is not in the phonebook");
        // And hash table
        if (fastHashTablePhoneBook.getType() == Dictionary.Type.HASHTABLE) {
            assertNull(fastHashTablePhoneBook.find(new Person("Antti", "Juustila")), () -> "Hashtable: should return null when person is not in the phonebook");
            assertNull(fastHashTablePhoneBook.find(new Person("Pertti", "Karhapää")), () -> "Hashtable: should return null when person is not in the phonebook");
            assertNull(fastHashTablePhoneBook.find(new Person("Jouni", "Lappalainen")), () -> "Hashtable: should return null when person is not in the phonebook");    
        }
        if (fastBSTPhoneBook.getType() == Dictionary.Type.BST) {
            assertNull(fastBSTPhoneBook.find(new Person("Antti", "Juustila")), () -> "BST: should return null when person is not in the phonebook");
            assertNull(fastBSTPhoneBook.find(new Person("Pertti", "Karhapää")), () -> "BST: should return null when person is not in the phonebook");
            assertNull(fastBSTPhoneBook.find(new Person("Jouni", "Lappalainen")), () -> "BST: should return null when person is not in the phonebook");    
        }
        System.out.println("...Finished searching persons not in the phonebooks.");
    }

    static private void readPersonsFromFile(Dictionary<Person, PhoneNumber> toPhoneBook) throws IOException {
        BufferedReader phoneBookReader = new BufferedReader(new FileReader("PhoneBook.txt", StandardCharsets.UTF_8));
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

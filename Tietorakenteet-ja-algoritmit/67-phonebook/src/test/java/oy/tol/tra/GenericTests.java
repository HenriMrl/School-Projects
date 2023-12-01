package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import oy.tol.tra.Dictionary.Type;

@DisplayName("Testing that the implementations are really generic.")
public class GenericTests {

    // Implementations to test:
    static Dictionary<String, Integer> slowArray = null;
    static Dictionary<String, Integer> fastHashTable = null;
    static Dictionary<String, Integer> fastBST = null;
    static final int TEST_COUNT = 100;
    static Pair<String, Integer> [] array;
    static int index = 0;

    // Test classes

    @BeforeAll
    static void readFromFile() {
        slowArray = new KeyValueArray<>(25);
        fastHashTable = new KeyValueHashTable<>(25);
        fastBST = new KeyValueBSearchTree<>();
    }

    @Test
    @DisplayName("Tests KeyValueArray, slow linear array")
    void testSlowArray() {
        try {
            List<String> randomList = new ArrayList<>();
            for (int index = 0; index < TEST_COUNT; index++) {
                randomList.add(String.valueOf(index));
            }
            Collections.shuffle(randomList);
            for (index = 0; index < TEST_COUNT; index++) {
                assertDoesNotThrow( () -> slowArray.add(randomList.get(index), Integer.parseInt(randomList.get(index))), () -> "add must not throw");
            }
            System.out.println(">> Testing key-value array with " + slowArray.size() + " entries");
            assertEquals(TEST_COUNT, slowArray.size(), () -> "Inserted count must match");
            for (index = 0; index < TEST_COUNT; index++) {
                Integer value = slowArray.find(String.valueOf(index));
                assertEquals(value, index, () -> "Inserted and retrieved values must match");
            }
            assertNull(slowArray.find("1999"), () -> "Must return null when element not in table");
            assertDoesNotThrow(() -> array = slowArray.toSortedArray(), "toSortedArray must not throw");
            assertTrue(isSorted(array), () -> "slowArray.toSortedArray() does not sort correctly");
            assertEquals(randomList.size(), array.length, () -> "Test array and toSortedArray lengths do not match");
            Object [] originalArray = randomList.toArray();
            Arrays.sort(originalArray);
            for (int index = 0; index < TEST_COUNT; index++) {
                assertTrue(originalArray[index].equals(array[index].getKey()), () -> "Array elements do not match");
            }    
            int elements = slowArray.size();
            assertDoesNotThrow(() -> slowArray.compress(), () -> "Compressing the storate should not throw");
            assertEquals(elements, slowArray.size(), () -> "After compression must have same number of elements than before");
            assertDoesNotThrow(() -> array = slowArray.toSortedArray(), "toSortedArray must not throw");
            for (int index = 0; index < TEST_COUNT; index++) {
                assertTrue(originalArray[index].equals(array[index].getKey()), () -> "Array elements do not match");
            }    
        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    @DisplayName("Tests hash table")
    void testHashTable() {
        try {
            if (fastHashTable.getType() == Type.NONE) {
                System.out.println("Not testing hash table yet since it has not been implemented.");
                return;
            }
            List<String> randomList = new ArrayList<>();
            for (int index = 0; index < TEST_COUNT; index++) {
                randomList.add(String.valueOf(index));
            }
            Collections.shuffle(randomList);
            for (index = 0; index < TEST_COUNT; index++) {
                assertDoesNotThrow( () -> fastHashTable.add(randomList.get(index), Integer.parseInt(randomList.get(index))), () -> "add must not throw");
            }
            System.out.println(">> Testing hash table with " + fastHashTable.size() + " entries");
            assertEquals(TEST_COUNT, fastHashTable.size(), () -> "Inserted count must match");
            for (int index = 0; index < TEST_COUNT; index++) {
                Integer value = fastHashTable.find(String.valueOf(index));
                assertEquals(value, index, () -> "Inserted and retrieved values must match");
            }
            assertNull(fastHashTable.find("1999"), () -> "Must return null when element not in table");
            assertDoesNotThrow(() -> array = fastHashTable.toSortedArray(), "toSortedArray must not throw");
            assertTrue(isSorted(array), () -> "KeyValueHashTable.toSortedArray() does not sort correctly");
            assertEquals(randomList.size(), array.length, () -> "Test array and toSortedArray lengths do not match");
            Object [] originalArray = randomList.toArray();
            Arrays.sort(originalArray);
            for (int index = 0; index < TEST_COUNT; index++) {
                assertTrue(originalArray[index].equals(array[index].getKey()), () -> "Array elements do not match");
            }    
            int elements = fastHashTable.size();
            assertDoesNotThrow(() -> fastHashTable.compress(), () -> "Compressing the storate should not throw");
            assertEquals(elements, fastHashTable.size(), () -> "After compression must have same number of elements than before");
            assertDoesNotThrow(() -> array = fastHashTable.toSortedArray(), "toSortedArray must not throw");
            for (int index = 0; index < TEST_COUNT; index++) {
                assertTrue(originalArray[index].equals(array[index].getKey()), () -> "Array elements do not match");
            }    
        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    @DisplayName("Tests BST")
    void testBST() {
        try {
            if (fastBST.getType() == Type.NONE) {
                System.out.println("Not testing BST yet since it has not been implemented.");
                return;
            }
            List<String> randomList = new ArrayList<>();
            for (int index = 0; index < TEST_COUNT; index++) {
                randomList.add(String.valueOf(index));
            }
            Collections.shuffle(randomList);
            for (int index = 0; index < TEST_COUNT; index++) {
                fastBST.add(randomList.get(index), Integer.parseInt(randomList.get(index)));
            }
            System.out.println(">> Testing BST with " + fastBST.size() + " entries");
            assertEquals(TEST_COUNT, fastBST.size(), () -> "Inserted count must match");
            for (int index = 0; index < TEST_COUNT; index++) {
                Integer value = fastBST.find(String.valueOf(index));
                assertEquals(value, index, () -> "Inserted and retrieved values must match");
            }
            assertNull(fastBST.find("1999"), () -> "Must return null when element not in tree");
            Pair<String, Integer> [] array = fastBST.toSortedArray();
            assertTrue(isSorted(array), () -> "KeyValueBSearchTree.toSortedArray() does not sort correctly");
            assertEquals(randomList.size(), array.length, () -> "Test array and toSortedArray lengths do not match");
            Object [] originalArray = randomList.toArray();
            Arrays.sort(originalArray);
            for (int index = 0; index < TEST_COUNT; index++) {
                assertTrue(originalArray[index].equals(array[index].getKey()), () -> "Array elements do not match");
            }
            int elements = fastBST.size();
            assertDoesNotThrow(() -> fastBST.compress(), () -> "Compressing the storage should not throw");
            assertEquals(elements, fastBST.size(), () -> "After compression must have same number of elements than before");

        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    private <T extends Comparable<T>> boolean isSorted(Pair<String, Integer> [] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i].getKey().compareTo(array[i + 1].getKey()) > 0)
                return false;
        }
        return true;
    }
}

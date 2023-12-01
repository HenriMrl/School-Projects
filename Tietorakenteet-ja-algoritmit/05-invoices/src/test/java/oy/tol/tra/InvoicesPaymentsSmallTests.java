package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Testing invoice checking algorithm")
public class InvoicesPaymentsSmallTests {

   @Test
   @DisplayName("Slow small test for creating new invoices")
   void newInvoicesFromPaymentsSlowTest() {
      try {
         System.out.println("Starting to handle the invoices the slow way...");
         InvoiceInspector inspector = new InvoiceInspector();
         inspector.readInvoicesAndPayments("invoices.txt", "payments.txt");
         long start = System.nanoTime();
         inspector.handleInvoicesAndPaymentsSlow();
         long duration = System.nanoTime() - start;
         inspector.saveNewInvoices("to-collect-slow.txt");
         System.out.println("Handling slowly the small invoices & payments files took " + duration / 1000000.0 + " ms");
         assertTrue(checkFileHash("to-collect-slow.txt", "cfb6ef9f00737b6393b9f66b49528c88dc248ffe"), () -> "Wrong hash code for the file.");
      } catch (IOException e) {
         fail("Failed to manage the invoice and payments files");
      }
   }

   @Test
   @DisplayName("Faster small test for creating new invoices")
   void newInvoicesFromPaymentsFastTest() {
      try {
         InvoiceInspector inspector = new InvoiceInspector();
         inspector.readInvoicesAndPayments("invoices.txt", "payments.txt");
         long start = System.nanoTime();
         inspector.handleInvoicesAndPaymentsFast();
         long duration = System.nanoTime() - start;
         inspector.saveNewInvoices("to-collect.txt");
         System.out.println("Handling fast the small invoices & payments files took " + duration / 1000000 + " ms");
         assertTrue(checkFileHash("to-collect.txt", "cfb6ef9f00737b6393b9f66b49528c88dc248ffe"), () -> "Wrong hash code for the file.");
      } catch (IOException e1) {
         fail("Failed to manage the invoice and payments files");
      }
   }

   private boolean checkFileHash(String fileName, String correctHash) {
      try {
         // Use SHA for hashing, does not need to be secure.
         MessageDigest md = MessageDigest.getInstance("SHA");
         // Use buffered reader since we need to read explicitly using UTF-8.
         BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

         // Read the lines from the line to a String, ignoring the last timestamp value.
         String line = null;
         StringBuilder sb = new StringBuilder();
         while ((line = br.readLine()) != null) {
            int lastComma = line.lastIndexOf(",");
            sb.append(line.substring(0, lastComma-1));
         }
         br.close();
         // Calculate the SHA digest (hash) from the string
         byte [] mdbytes = md.digest(sb.toString().getBytes("UTF-8"));

         // Convert the hash into a String.
         StringBuilder hashStringBuilder = new StringBuilder();
         for (int i = 0; i < mdbytes.length; i++) {
            hashStringBuilder.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
         }
         String calculatedHash = hashStringBuilder.toString();
         System.out.println("\nHash is " + calculatedHash.toString());
         // Check if the hash matches with the expected correct one.
         boolean matches = calculatedHash.equals(correctHash);
         if (!matches) {
            System.out.println("Correct has is: " + correctHash);
         }
         return matches;
      } catch (NoSuchAlgorithmException | IOException e) {
         System.out.println("Could not check file hash: " + e.getMessage());
      }
      return false;
   }
}

package oy.tol.tra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class BrailleConverter {
   
   public enum Converter {
      SLOW,
      HASHTABLE
   }
   private BrailleTable brailleTable;

   public void initialize(Converter whichConverter, String brailleSymbolTableFileName) throws InvalidBrailleFileFormatException {
      switch (whichConverter) {
         case SLOW -> brailleTable = new SlowBrailleTable();
         case HASHTABLE -> brailleTable = new HashBrailleTable();
      }
      brailleTable.readSymbols(brailleSymbolTableFileName);
   }

   public boolean convertTextFileToBraille(String textFile, String outputFile)  {
      BufferedReader bufferedReader = null;
      BufferedWriter bufferedWriter = null ;
      try {
         bufferedReader = new BufferedReader(new FileReader(new File(textFile), StandardCharsets.UTF_8)); 
         bufferedWriter = new BufferedWriter(new FileWriter(new File(outputFile), StandardCharsets.UTF_8));
         String lineIn;
         while ((lineIn = bufferedReader.readLine()) != null) {
            StringBuilder builder = new StringBuilder();
            if (lineIn.length() > 0) {
               boolean onNumberSequence = false;
               for (int index = 0; index < lineIn.length(); index++) {
                  char character = lineIn.charAt(index);
                  Character symbol = brailleTable.lookupBrailleSymbol(character);
                  if (symbol != null) {
                     if (Character.isUpperCase(character)) {
                        builder.append(BrailleSymbol.CAPITAL_LETTER_FOLLOWS);
                        onNumberSequence = false;
                     } else if (Character.isDigit(character)) {
                        if (!onNumberSequence) {
                           builder.append(BrailleSymbol.NUMBER_FOLLOWS);
                           onNumberSequence = true;
                        }
                     } else {
                        onNumberSequence = false;
                     }
                     builder.append(symbol);
                  }
               }
            }
            builder.append("\n");
            bufferedWriter.append(builder.toString());
         }
         bufferedWriter.flush();
         
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      } finally {
         try {
            if (bufferedWriter != null) bufferedWriter.close();
            if (bufferedReader != null) bufferedReader.close();   
         } catch (Exception e) {
            System.out.println("Failed to close readers and writers " + e.getMessage());
         }
      }
      return true;
   }
}

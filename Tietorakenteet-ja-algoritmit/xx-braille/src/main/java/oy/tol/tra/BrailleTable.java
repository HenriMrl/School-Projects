package oy.tol.tra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BrailleTable {

   public static final char UNKNOWN_CHAR_OR_SYMBOL = '_';

   /**
    * This method is called right after your BrailleTable subclass has been instantiated.
    * Here you should prepare your table to handle Character / Braille symbol pairs when
    * later the {@code addToTable(Character character, Character brailleSymbol)} is called.
    *
    * Reserve any memory you need, prepare other internal member variables you may need etc. 
    */
   protected abstract void initializeTable();

   public final void readSymbols(String fromFile) throws InvalidBrailleFileFormatException {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fromFile), StandardCharsets.UTF_8))) {
         String line;
         initializeTable();
         while ((line = bufferedReader.readLine()) != null) {
            if (line.length() > 0) {
               String [] elements = line.split("\\t");
               if (elements.length == 2) {
                  addToTable(elements[0].charAt(0), elements[1].charAt(0));
               } else {
                  throw new InvalidBrailleFileFormatException("Invalid Braille symbol table file at " + fromFile);
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
         throw new InvalidBrailleFileFormatException("Could not read Braille symbol table: " + e.getMessage());
      }
   }

   /**
    * This method is called when the {@code readSymbols(String fromFile)} reads the symbol table file into memory.
    *
    * Your implementation should store the character and symbol pairs so that they are associated together,
    * and that it is as fast as possible to look up a Braille symbol using a "normal" character, when 
    * calling {@code lookupBrailleSymbol(Character forCharacter)}.
    *
    * @param character The "normal" character in the written language.
    * @param brailleSymbol The corresponding Braille symbol for the character.
    */
   protected abstract void addToTable(Character character, Character brailleSymbol);

   /**
    * Looks up a Braille symbol using a "normal" character. For example,
    * when <code>forCharacter</code> has 'K', the returned value must be '⠅'.
    * @param forCharacter
    * @return Returns the corresponding Braille character.
    */
   public abstract Character lookupBrailleSymbol(Character forCharacter);

   /**
    * Looks up a "normal" character using a Braille symbol. For example,
    * when <code>forBrailleSymbol</code> has '⠅', the returned value must be 'K'.
    * @param forBrailleSymbol
    * @return Returns the corresponding "normal" character.
    */
   public abstract Character lookupCharacter(Character forBrailleSymbol);
}

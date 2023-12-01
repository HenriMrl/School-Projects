package oy.tol.tra;

public class BrailleSymbol {
   char character;
   char braille;

   public static final char CAPITAL_LETTER_FOLLOWS = '⠠';
   public static final char NUMBER_FOLLOWS = '⠼';

   public BrailleSymbol(char character, char brailleSymbol) {
      this.character = character;
      this.braille = brailleSymbol;
   }
}

package oy.tol.tra;

public class SlowBrailleTable extends BrailleTable {

   private BrailleSymbol[] brailleTable;
   private int tableSize = 0;
   private static final int DEFAULT_TABLE_SIZE = 50;
   private int addIndex;

   @Override
   protected void initializeTable() {
      tableSize = DEFAULT_TABLE_SIZE;
      brailleTable = new BrailleSymbol[tableSize];
      addIndex = 0;
   }

   @Override
   protected void addToTable(Character character, Character brailleSymbol) {
      if (addIndex >= brailleTable.length) {
         int newSize = tableSize * 2;
         BrailleSymbol [] newTable = new BrailleSymbol[newSize];
         for (int i = 0; i < tableSize; i++) {
            if (brailleTable[i] == null) {
               break;
            }
            newTable[i] = brailleTable[i];
         }
         tableSize = newSize;
         brailleTable = newTable;
      }
      brailleTable[addIndex++] = new BrailleSymbol(character, brailleSymbol);
   }

   @Override
   public Character lookupBrailleSymbol(Character forCharacter) {
      for (int i = 0; i < brailleTable.length; i++) {
         if (brailleTable[i] == null) {
            break;
         }
         if (brailleTable[i].character == Character.toUpperCase(forCharacter)) {
            return brailleTable[i].braille;
         }
      }
      return UNKNOWN_CHAR_OR_SYMBOL;
   }

   @Override
   public Character lookupCharacter(Character forBrailleSymbol) {
      for (int i = 0; i < brailleTable.length; i++) {
         if (brailleTable[i] == null) {
            break;
         }
         if (brailleTable[i].braille == forBrailleSymbol) {
            return brailleTable[i].character;
         }
      }
      return UNKNOWN_CHAR_OR_SYMBOL;
   }

}

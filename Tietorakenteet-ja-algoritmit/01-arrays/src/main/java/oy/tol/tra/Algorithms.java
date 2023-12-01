package oy.tol.tra;
// kaikki kurssin algoritmit

public class Algorithms {
    
    public static <T extends Comparable<T>> void sort(T [] array) {
      boolean vaihto = true;
      while(vaihto){
         vaihto = false;
         for(int i = array.length-1; i > 0; i--){
            if(array[i].compareTo(array[i-1]) < 0){
               vaihto = true;
               swap(array,i,i-1);
            }
         }
      }

      
 }

    public static <T> void reverse(T [] array) {
      int i = 0;
      while (i < array.length/2) {
         T temp = array[i];
         array[i] = array[array.length-i-1];
         array[array.length-i-1] = temp;
         i++;
     }
}
public static <T> void swap(T [] array, int first, int second){
   T tmp = array[first];
   array[first] = array[second];
   array[second] = tmp;
}


}


package oy.tol.tra;

/**
 * A simple array of student grades to be used in testing
 * misbehaving algorithm for reversing the array.
 */
public class Grades {
   
   private Integer [] grades = null;

   /**
    * A constructor for building IntArrays.
    * @param grades the plain Java integer array with numbers to add.
    */
   public Grades(Integer [] grades) {
      this.grades = new Integer [grades.length];
      for (int counter = 0; counter < grades.length; counter++) {
         this.grades[counter] = grades[counter];
      }
   }

   /**
    * The method to reverse the internal Java int array.
    */
   public void reverse() {
      /* TODO:
       1. Execute the tests following the instructions.
       2. Study the code below and try to find what is the issue.
       3. Use the debugger to see the execution and variable values if necessary.
       4. Fix the issue.
       5. Transform the algorithm to a generic one as instructed in the readme file.
      */
      int i = 0;
      while (i < grades.length/2) {
         int temp = grades[i];
         grades[i] = grades[grades.length-i-1];
         grades[grades.length-i-1] = temp;
         i++;
     }
   }

   /**
    * Sorts the array to ascending order.
    */
   public void sort() {
      /* TODO:
       1. Execute the tests following the instructions.
       2. Study the code below and try to find what is the issue.
       3. Use the debugger to see the execution and variable values if necessary.
       4. Fix the issue.
       5. Transform the algorithm to a generic one as instructed in the readme file.
      */
      boolean vaihto = true;
      while(vaihto){
         vaihto = false;
         for(int i = grades.length-1; i > 0; i--){
            if(grades[i] < grades[i-1]){
               vaihto = true;
               int tmp = grades[i];
               grades[i] = grades[i-1];
               grades[i-1] = tmp;
            }
         }
      }
   }
  

   /**
    * Returns the plain Java int [] array for investigation.
    * @return The int array.
    */
   public Integer [] getArray() {
      return grades;
   }
}

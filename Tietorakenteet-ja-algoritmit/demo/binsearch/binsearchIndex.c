#include <stdio.h>
#include <limits.h>

/*
 This demo is about index calculation in binary search algorithm.
 An example on how NOT to calculate a middle index to an array,
 and how to calculate it. If you use large integers and the array is
 very large, the first way will result to an integer overflow and
 accessing the array out of bounds.
 
 In this sample, there is no array but calculating the index would happen
 in a similar way in binary search and other algorithms that need to
 calculate the middle index from an array on the larger half of the array.
 */
int main(void) {
  int low = (double)INT_MAX * 0.65;
  int high = low + 9000;
  int middle = (low + high) / 2;
  printf("low is: %d, high is: %d\n", low, high);
  printf("low + high is: %d\n", low + high);
  printf("INT_MAX: %d, low: %d, high: %d, middle: %d\n", INT_MAX, low, high, middle);
  int betterMiddle = low + (high - low) / 2;
  printf("betterwayIndex is: %d\n", betterMiddle);
  return 0;
}

public class Utility {
	
	public static <T extends Comparable<T>> T max(T a, T b) {
		return a.compareTo(b) > 0 ? a : b;
	} 

	// public static int max(int a, int b) {
	// 	return a > b ? a : b;
	// }

	// public static double max(double a, double b) {
	// 	return a > b ? a : b;
	// }

}

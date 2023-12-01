public class MyArrays {

	public static <T> void copy(T [] fromArray, int fromIndex, int count, T [] toArray, int toIndex) {
		if (fromIndex < 0 || fromIndex + count > fromArray.length) {
			throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
		}
		if (toIndex < 0 || toIndex + count > toArray.length) {
			throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
		}
		int readIndex = fromIndex;
		int writeIndex = toIndex;
		int counter = 0;
		while (counter < count) {
			toArray[writeIndex++] = fromArray[readIndex++];
			counter++;
		}
	}

	// public static void copy(Integer[] fromArray, int fromIndex, int count, Integer[] toArray, int toIndex) {
	// 	if (fromIndex + count > fromArray.length) {
	// 		throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
	// 	}
	// 	if (toIndex + count > toArray.length) {
	// 		throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
	// 	}
	// 	int readIndex = fromIndex;
	// 	int writeIndex = toIndex;
	// 	int counter = 0;
	// 	while (counter < count) {
	// 		toArray[writeIndex++] = fromArray[readIndex++];
	// 		counter++;
	// 	}
	// }
	
	// public static void copy(String[] fromArray, int fromIndex, int count, String[] toArray, int toIndex) {
	// 	if (fromIndex + count > fromArray.length) {
	// 		throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
	// 	}
	// 	if (toIndex + count > toArray.length) {
	// 		throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
	// 	}
	// 	int readIndex = fromIndex;
	// 	int writeIndex = toIndex;
	// 	int counter = 0;
	// 	while (counter < count) {
	// 		toArray[writeIndex++] = fromArray[readIndex++];
	// 		counter++;
	// 	}
	// }












	// public static <T> void copy(T[] fromArray, int fromIndex, int count, T[] toArray, int toIndex) {
	// 	if (fromIndex + count > fromArray.length) {
	// 		throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
	// 	}
	// 	if (toIndex + count > toArray.length) {
	// 		throw new ArrayIndexOutOfBoundsException("Cannot copy array out of bounds");
	// 	}
	// 	int readIndex = fromIndex;
	// 	int writeIndex = toIndex;
	// 	int counter = 0;
	// 	while (counter < count) {
	// 		toArray[writeIndex++] = fromArray[readIndex++];
	// 		counter++;
	// 	}
	// }

}

public class UniqueStringBucket {
	
	private String [] values;
	private int count = 0;

	public UniqueStringBucket(int size) {
		values = new String[size];
	}

	boolean add(String value) throws OutOfMemoryError {
		for (int index = 0; index < count; index++) {
			if (values[index].compareTo(value) == 0) {
				return false;
			}
		}
		values[count++] = value;
		if (count >= values.length) {
			reallocate();
		}
		return true;
	}

	int count() {
		return count;
	}

	String get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > count - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds in get(int)");
		}
		return values[index];
	}

	private void reallocate() throws OutOfMemoryError {
		int newSize = count * 2;
		String [] tempArray = new String[newSize];
		for (int i = 0; i < count; i++) {
			tempArray[i] = values[i];
		}
		values = tempArray;
	}
}

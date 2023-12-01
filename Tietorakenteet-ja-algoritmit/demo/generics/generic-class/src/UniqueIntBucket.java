public class UniqueIntBucket {
	
	private Integer [] values;
	private int count = 0;

	public UniqueIntBucket(int size) {
		values = new Integer[size];
	}

	boolean add(Integer value) throws OutOfMemoryError {
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

	Integer get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > count - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds in get(int)");
		}
		return values[index];
	}

	private void reallocate() throws OutOfMemoryError {
		int newSize = count * 2;
		Integer [] tempArray = new Integer[newSize];
		for (int i = 0; i < count; i++) {
			tempArray[i] = values[i];
		}
		values = tempArray;
	}
}

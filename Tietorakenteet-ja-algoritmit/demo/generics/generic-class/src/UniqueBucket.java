public class UniqueBucket<T extends Comparable<T>> {
	
	private Object [] values;
	private int count = 0;

	public UniqueBucket(int size) {
		values = new Object[size];
	}

	boolean add(T value) throws OutOfMemoryError {
		for (int index = 0; index < count; index++) {
			@SuppressWarnings("unchecked")
			Comparable<T> something = (Comparable<T>)values[index];
			if (something.compareTo(value) == 0) {
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

	@SuppressWarnings("unchecked")
	T get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > count - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds in get(int)");
		}
		return (T)values[index];
	}

	private void reallocate() throws OutOfMemoryError {
		int newSize = count * 2;
		Object [] tempArray = new Object[newSize];
		for (int i = 0; i < count; i++) {
			tempArray[i] = values[i];
		}
		values = tempArray;
	}
}

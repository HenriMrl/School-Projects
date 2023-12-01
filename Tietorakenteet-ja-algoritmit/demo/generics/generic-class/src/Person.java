public class Person implements Comparable<Person> {
	private String firstName;
	private String lastName;
	private int age = 0;

	public Person(String first, String last) {
		firstName = first;
		lastName = last;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	@Override
	public int compareTo(Person o) {
		if (null != o) {
			return this.toString().compareTo(o.toString());
		}
		return -1;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Person) {
			return this.compareTo((Person)o) == 0;
		}
		return false;
	}
}

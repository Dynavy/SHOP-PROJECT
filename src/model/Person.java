package model;


// We create our abstract class Person.
public abstract class Person {

	protected static String name = null;

	// Constructor for client.
	public Person(String name) {
		Person.name = name;

	}

	// Constructor for employee.
	public Person() {

	}

	// Method to return the text in Upper Case.
	public String toString() {
		
		return name.toUpperCase();
	}

}

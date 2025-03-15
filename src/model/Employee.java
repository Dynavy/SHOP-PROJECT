package model;

import dao.Dao;
import dao.DaoImplMongoDB;
import main.Logable;

public class Employee extends Person implements Logable {

	// Polymorphism enables accessing DaoImpl functionalities through the dao object.
	public Dao dao = new DaoImplMongoDB();

	public Employee(String username, String pw) {
		super(name);

	}

	public Employee() {

	}

	@Override
	public boolean login(String user, String password)  {
	
		// If we have matches, we store it on the credentials instance.
		Employee credentials = dao.getEmployee(user, password);
		// If we have no matches, we return null.
		return credentials != null;
	}
}
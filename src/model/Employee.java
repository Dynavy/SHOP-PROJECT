package model;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {

	// Polymorphism enables accessing DaoImpl functionalities through the dao object.
	public Dao dao = new DaoImplJDBC();

	public Employee(int employee_id, String pw) {
		super(name);

	}

	public Employee() {

	}

	public boolean login(int user, String password) {
		
		// We made the connection to the database.
		dao.connect();
		// If we have matches, we store it on the credentials instance.
		Employee credentials = dao.getEmployee(user, password);
		// We close the conection to the database after checking the credentials.
		dao.disconnect();
		// If we have no matches, we return null.
		return credentials != null;
	}
}
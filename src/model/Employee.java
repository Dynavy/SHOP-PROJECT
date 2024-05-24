package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {

	//	private int EMPLOYEE_ID;
	//	private String PASSWORD;
	//	private int employeeID = EMPLOYEE_ID;
	
	// Polymorphism enables accessing DaoImpl functionalities through the dao object.
	public Dao dao = new DaoImplJDBC();

	public Employee(int employee_id, String pw) {
		super(name);

	}

	public Employee() {

	}

	public boolean login(int user, String password) {

		//		if (user == EMPLOYEE_ID && password.equals(PASSWORD)) {
		//			
		//			return true;
		//		}
		//		return false;

		
		// Connects to the database, invokes the getEmployee method and then disconnects from the database. 
		try  {
			
		    dao.connect();
		    Employee credentials = dao.getEmployee(user, password);
		    dao.disconnect();
		    return credentials != null;
		    
		} catch (SQLException SQLError) {
		  
		    SQLError.printStackTrace();
		}
		
		return false;
	}
}

package model;

import main.Logable;

public class Employee extends Person implements Logable {



	private final int EMPLOYEE_ID = 123;
	private final String PASSWORD = "test";
	private int employeeID = EMPLOYEE_ID;

	
	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}
	
	public Employee() {
		super();
	}

	public boolean login(int user, String password) {
		if (user == EMPLOYEE_ID && password.equals(PASSWORD)) {
			return true;
		}
		return false;

	}
}

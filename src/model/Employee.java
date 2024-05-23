package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {

	private int EMPLOYEE_ID;
	private String PASSWORD;
	private int employeeID = EMPLOYEE_ID;
	public Dao dao;

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

	public Employee(int employee_id, String pw) {
		super();
		this.PASSWORD = pw;
		this.EMPLOYEE_ID = employee_id;
		dao = new DaoImplJDBC();
	}

	public Employee() {

	}

	public boolean login(int user, String password) {
		
//		if (user == EMPLOYEE_ID && password.equals(PASSWORD)) {
//			
//			return true;
//		}
//		return false;
		
		try {
			dao.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Employee credentials = dao.getEmployee(user, password);
		
		try {
			dao.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (credentials != null) ? true : false;

	}
}

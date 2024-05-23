package dao;

import java.sql.SQLException;

import model.Employee;

public interface Dao {

	void connect() throws SQLException;
	void disconnect() throws SQLException;
	Employee getEmployee(int user, String pw, String name); 
}

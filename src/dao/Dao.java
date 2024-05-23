package dao;

import model.Employee;

public interface Dao {

	void connect();
	void disconnect();
	Employee getEmployee(String user, int pw);
}

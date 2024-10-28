package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Product;

public interface Dao {

	void connect();
	void disconnect();
	Employee getEmployee(int user, String pw);
	// New methods to manage the inventory.
	ArrayList<Product> getInventory();
	boolean writeInventory(ArrayList<Product> product);
}

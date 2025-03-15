package dao;

import java.util.ArrayList;
import model.Employee;
import model.Product;

public interface Dao {

	void connect();

	void disconnect();

	Employee getEmployee(String user, String pw);

	// New methods to manage the inventory.
	ArrayList<Product> getInventory();

	boolean writeInventory(ArrayList<Product> product);

	void addProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(Product product);
}

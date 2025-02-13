package dao;

import java.util.ArrayList;

import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao{

	@Override
	public void connect() {
		
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public Employee getEmployee(int user, String pw) {
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		return false;
	}

	@Override
	public void addProduct(Product product) {
		
	}

	@Override
	public void updateProduct(Product product) {
		
	}

	@Override
	public void deleteProduct(Product product) {
		
	}

}

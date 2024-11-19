package dao;

import java.util.ArrayList;

import dao.jaxb.JaxbUnMarshaller;
import model.Employee;
import model.Product;
import model.ProductList;

public class DaoImplJaxb implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getEmployee(int user, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {

		JaxbUnMarshaller unMarshaller = new JaxbUnMarshaller();
		ProductList productList = unMarshaller.init();

		if (productList != null) {
			return new ArrayList<>(productList.getProducts());
		}
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		// TODO Auto-generated method stub
		return false;
	}

}

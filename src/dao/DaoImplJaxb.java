package dao;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import dao.jaxb.JaxbMarshaller;
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
	public Employee getEmployee(String user, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {

		try {
			JaxbUnMarshaller unMarshaller = new JaxbUnMarshaller();

			ProductList productList = unMarshaller.init();

			if (productList != null) {
				return new ArrayList<>(productList.getProducts());
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {

		try {

			JaxbMarshaller marshaller = new JaxbMarshaller();

			ProductList productList = new ProductList();
			productList.setProducts(product);
			// We invoke the init method to export the products.
			return marshaller.init(productList);

		} catch (Exception error) {
			error.printStackTrace();
			return false;
		}
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}
}
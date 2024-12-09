package dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

import main.Shop;
import model.Employee;
import model.Product;

public class DaoImplXml implements Dao {

	@Override
	public ArrayList<Product> getInventory() {

		// ArrayList to store the default products.
		ArrayList<Product> products = new ArrayList<>();

		// New instance of SAXParserFactory.
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// Initialize a SAXParser.
		SAXParser parser;

		// New instance of saxReader.
		dao.xml.SaxReader saxReader = new dao.xml.SaxReader();

		try {
			parser = factory.newSAXParser();
			// Path of the XML file.
			File file = new File("xml/inputInventory.xml");
			parser.parse(file, saxReader);
			// We store the products on our arrayList.
			products = saxReader.getProducts();
		} catch (ParserConfigurationException | SAXException error) {
			System.out.println("ERROR creating the parser");
		} catch (IOException error) {
			System.out.println("ERROR file not found");
		}

		// We return the array with the products.
		return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {

		dao.xml.DomWriter domWriter = new dao.xml.DomWriter();
		// Invoke the generateXml() methoh with his boolean.
		return domWriter.generateXml(product);
	}

	@Override
	public void connect() {

	}

	@Override
	public void disconnect() {

	}

	@Override
	public Employee getEmployee(int user, String pw) {
		// TODO Auto-generated method stub
		return null;
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

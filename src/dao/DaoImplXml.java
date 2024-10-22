package dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import model.Employee;
import model.Product;

public class DaoImplXml implements Dao{


	@Override
	public ArrayList<Product> getInventory() {

		// ArrayList that will contain the default products.
		ArrayList<Product> products = new ArrayList<>();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			// Path of the XML file.
			File file = new File ("xml/inputInventory.xml");
			dao.xml.SaxReader saxReader = new dao.xml.SaxReader();
			parser.parse(file, saxReader);
			
		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("ERROR creating the parser");
		} catch (IOException e) {
			System.out.println("ERROR file not found");
		}
		
		// We return the array with the products.
		return products;
	}
	
	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int user, String pw) {
		// TODO Auto-generated method stub
		return null;
	}


}

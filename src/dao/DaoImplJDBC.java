package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

	private Connection connection;
	
	
	public DaoImplJDBC() {
		
	}

	@Override
	public void connect() throws SQLException {

		String url = "jdbc:mysql://localhost:6788/ShopDB";
		String user = "root";
		String pass = "root";
		this.connection = DriverManager.getConnection(url, user, pass);

	}

	@Override
	public void disconnect() throws SQLException {

		if (connection != null) {
			connection.close();
		}
	}

	@Override
	public Employee getEmployee(int user, String pw) {

		Employee employee = null;
		// Prepare SQL query.
		String query = "select * from Employee where Employee_ID = ? AND password = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			// Set userID and password parameters.
			ps.setInt(1,user);
			ps.setString(2, pw);
		  	
	        try (ResultSet rs = ps.executeQuery()) {
	        	// If we find any data, we create an Employee object.
	        	if (rs.next()) {
	        		// Creates an Employee instance to temporarily store the user and password data retrieved from the database.
	        		employee =  new Employee(user, pw);            		            				
	        	}
	        }
	    } catch (SQLException SQLError) {
			// in case error in SQL
			SQLError.printStackTrace();
		}
		// We return the employee object.
		return employee;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}


		
	}
	


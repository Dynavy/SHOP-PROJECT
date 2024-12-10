package dao;

import java.math.BigDecimal;
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
		connect();
	}

	@Override
	public void connect() {

		String url = "jdbc:mysql://localhost:6788/ShopDB";
		String user = "root";
		String pass = "root";

		try {
			this.connection = DriverManager.getConnection(url, user, pass);

		} catch (SQLException SqlError) {

			System.out.println("Error connecting with the database" + SqlError.getMessage());
			SqlError.printStackTrace();
		}
	}

	@Override
	public void disconnect() {

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException SqlError) {

				System.out.println("Error connecting with the database" + SqlError.getMessage());
				SqlError.printStackTrace();
			}
		}
	}

	@Override
	public Employee getEmployee(int user, String pw) {

		Employee employee = null;
		// Prepare SQL query.
		String query = "select * from Employee where Employee_ID = ? AND password = ?";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			// Set userID and password parameters.
			ps.setInt(1, user);
			ps.setString(2, pw);

			try (ResultSet rs = ps.executeQuery()) {
				// If we find any data, we create an Employee object.
				if (rs.next()) {
					// Creates an Employee instance to temporarily store the user and password data
					// retrieved from the database.
					employee = new Employee(user, pw);
				}
			}
		} catch (SQLException SqlError) {

			System.out.println("Error connecting with the database" + SqlError.getMessage());
			SqlError.printStackTrace();
		}
		// We return the employee object.
		return employee;
	}
	
	@Override
	public ArrayList<Product> getInventory() {
	
		ArrayList<Product> productsList = new ArrayList<>();
		// Prepare SQL query.
		String query = "SELECT * FROM Inventory";

		try (PreparedStatement ps = connection.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {
			        // Iterate through the result set.
			        while (rs.next()) {

			            Product products = new Product(
			                      rs.getString("Name"), 
			                      rs.getBigDecimal("wholesalerPrice").doubleValue(),
			                      rs.getBoolean("Available"),      
			                      rs.getInt("Stock") 
			            );
			            productsList.add(products);
			}
			        
		} catch (SQLException SqlError) {

			System.out.println("Error connecting with the database" + SqlError.getMessage());
			SqlError.printStackTrace();
		}
		// We return the Product object.
		return productsList;
	}

	@Override
	public void addProduct(Product product) {
	    String query = "INSERT INTO Inventory (Name, wholesalerPrice, Available, Stock) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, product.getName());
	        ps.setBigDecimal(2, BigDecimal.valueOf(product.getWholesalerPrice().getValue()));
	        ps.setBoolean(3, product.isAvailable());
	        ps.setInt(4, product.getStock());

	        ps.executeUpdate();
	    } catch (SQLException SqlError) {
	        System.out.println("Error adding product to the database: " + SqlError.getMessage());
	        SqlError.printStackTrace();
	    }
	}
	
	@Override
	public void updateProduct(Product product) {
	    String query = "UPDATE Inventory SET Stock = ? WHERE Name = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, product.getStock());
	        ps.setString(2, product.getName());

	        ps.executeUpdate();
	    } catch (SQLException SqlError) {
	        System.out.println("Error updating product stock in the database: " + SqlError.getMessage());
	        SqlError.printStackTrace();
	    }
	}

	@Override
	public void deleteProduct(Product product) {
	    String query = "DELETE FROM Inventory WHERE Name = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, product.getName()); 

	        ps.executeUpdate();
	    } catch (SQLException SqlError) {
	        System.out.println("Error deleting product from the database: " + SqlError.getMessage());
	        SqlError.printStackTrace();
	    }
	}
	
	@Override
	public boolean writeInventory(ArrayList<Product> product) {
		// TODO Auto-generated method stub
		return false;
	}
}

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
	public Employee getEmployee(String user, String pw) {

		Employee employee = null;
		// Prepare SQL query.
		String query = "select * from Employee where Employee_ID = ? AND password = ?";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			// Set userID and password parameters.
			ps.setString(1, user);
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

		try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

			// Iterate through the result.
			while (rs.next()) {
				Product product = new Product(rs.getString("Name"), rs.getBigDecimal("wholesalerPrice").doubleValue(),
						rs.getBoolean("Available"), rs.getInt("Stock"));

				// Set the ID after creating the product object.
				product.setId(rs.getInt("Id"));

				// Set the ProductID after creating the product object.
				product.setProductId(rs.getInt("Id"));

				productsList.add(product);
			}

		} catch (SQLException SqlError) {
			System.out.println("Error connecting with the database: " + SqlError.getMessage());
			SqlError.printStackTrace();
		}

		// Return the product list.
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
		String query = "UPDATE Inventory SET ProductID = ?, WholesalerPrice = ?, Available = ?, Stock = ? WHERE Name = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			
			ps.setInt(1, product.getProductId());
			ps.setBigDecimal(2, BigDecimal.valueOf(product.getWholesalerPrice().getValue()));
			ps.setBoolean(3, product.isAvailable());
			ps.setInt(4, product.getStock());
			ps.setString(5, product.getName());

			ps.executeUpdate();
		} catch (SQLException SqlError) {
			System.out.println("Error updating product in the database: " + SqlError.getMessage());
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
	public boolean writeInventory(ArrayList<Product> products) {
		String query = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, available, stock, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, NOW())";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			for (Product product : products) {
				ps.setInt(1, product.getProductId());
				ps.setString(2, product.getName());
				ps.setBigDecimal(3, BigDecimal.valueOf(product.getWholesalerPrice().getValue()));
				ps.setBoolean(4, product.isAvailable());
				ps.setInt(5, product.getStock());

				// Adds the SQL statement to the batch.
				ps.addBatch();
			}

			// Executes all batch statements at once.
			int[] affectedRows = ps.executeBatch();

			return affectedRows.length == products.size();
		} catch (SQLException SqlError) {
			System.out.println("Error inserting data into historical_inventory: " + SqlError.getMessage());
			SqlError.printStackTrace();
			return false;
		}
	}
}

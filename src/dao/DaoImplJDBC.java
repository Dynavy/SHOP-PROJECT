package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;

public class DaoImplJDBC implements Dao {

	private Connection connection;
	
	
	
	public DaoImplJDBC() {
		
	}

	@Override
	public void connect() throws SQLException {

		String url = "jdbc:mysql://localhost:8889/ShopDB";
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
		// prepare query
		String query = "select * from Employee where Employee_ID = ? ";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			// set id to search for
			ps.setInt(1,user);
			ps.setString(1, pw);
		  	//System.out.println(ps.toString());
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		employee =  new Employee(user, pw);            		            				
	        	}
	        }
	    } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		return employee;
	}
	

}

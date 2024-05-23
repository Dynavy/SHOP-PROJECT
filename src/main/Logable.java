package main;

import java.sql.SQLException;

public interface Logable {

	boolean login(int user, String password) throws SQLException;
	
}

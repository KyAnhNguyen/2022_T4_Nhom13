package utils;

import java.sql.*;

public class DatabaseConnection {
	Connection conn;
	public DatabaseConnection() {
		// TODO Auto-generated constructor stub
		
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		
	}
	/*
	 * CONNECTION DATABASE
	 */
	public Connection connect(String database_name) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/" + database_name,"root","");
		
		return conn;
	}
	
	
	
	public boolean setDatabase(String database_name) {
		return false;
	}
	
	public void disConnect() throws SQLException {
		conn.close();
	}
	
	
	

}
package utils;

import java.sql.*;

public class DatabaseConnection {
	Connection conn;
	public DatabaseConnection() {
		// TODO Auto-generated constructor stub
		
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/staging","root",""); 
		
	}
	
	public boolean connect(String database_name) throws SQLException {
		Connection con = DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/staging","root","");
		return true;
	}
	
	public boolean setDatabase(String database_name) {
		return false;
	}
	
	public void disConnect() throws SQLException {
		conn.close();
	}

}

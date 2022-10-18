package utils;

import java.sql.*;

public class DatabaseConnection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/staging","root",""); 
		
		
	}

}

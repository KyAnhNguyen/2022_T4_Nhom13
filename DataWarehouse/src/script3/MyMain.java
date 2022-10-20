package script3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class MyMain {

	DatabaseConnection dcon;
	Connection conn;

	public MyMain() throws SQLException {
		// TODO Auto-generated constructor stub
		dcon = new DatabaseConnection();
		
		
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		MyMain main = new MyMain();
		main.loadDataIntoTable("province", "E:\\Film-Schedule---Data-Warehouse\\province.csv", QUERIES.QueryTransformCSV.PROVINCE);
		System.out.println("hbybj");
	}

	public void loadDataIntoTable(String tableName, String url, String query) throws SQLException, ClassNotFoundException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, url);
//		ps.setString(2, tableName);
		ps.executeUpdate();
//		ResultSet rs = ps.executeQuery();
//		return rs.next();
	}

}

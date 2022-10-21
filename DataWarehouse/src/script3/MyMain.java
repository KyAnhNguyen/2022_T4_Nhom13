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
//		main.loadDataToDW("province", "province", "data_warehouse", QUERIES.PROVINCE.GET_NUMBER_ROW);
		boolean a = main.checkEqualRowInsert(3, QUERIES.PROVINCE.GET_NUMBER_ROW);
		System.out.println("--------------" + a);
	}

	public void loadDataIntoTable(String url, String query) throws SQLException, ClassNotFoundException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, url);
		ps.executeUpdate();
	}
	public void loadDataToDW(String tableOut, String tableIn, String databaseName, String queryTableRow) throws SQLException, ClassNotFoundException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.LOAD_DATA);
		ps.setString(1, tableOut);
		ps.setString(2, tableIn);
		ps.setString(3, databaseName);
		System.out.println(ps.executeQuery());
//		ResultSet rs = ps.executeQuery();
//		System.out.println(rs.getString(1));
	}
	
	public boolean checkEqualRowInsert(int numberInserted, String query) throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return numberInserted == rs.getInt(1);
	}

}

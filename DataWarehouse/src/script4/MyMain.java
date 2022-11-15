package script4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Table;
import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class MyMain {

	DatabaseConnection dcon;
	Connection conn;
	DateDimDao dateDimDao;
	PrizeDao prizeDao;
	ProvinceDao provinceDao;
	LottoDao lottoDao;

	public MyMain() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated constructor stub
		dcon = new DatabaseConnection();
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		dateDimDao = new DateDimDao(dcon);
		prizeDao = new PrizeDao(dcon);
		provinceDao = new ProvinceDao(dcon);
		lottoDao = new LottoDao(dcon);
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		MyMain main = new MyMain();
		System.out.println(main.loadAll());
//		System.out.println("giải");
	}

	public boolean loadAll() throws ClassNotFoundException, SQLException {
		if(this.checkLoad()) {
			this.conn.close();
			return true;
		}
		return false;
	}

	public boolean checkLoad() throws ClassNotFoundException, SQLException {
		if (this.getStatusLog().equals("FI")) {
			System.out.println("load");
			return this.dateDimDao.loadIntoDW() && this.prizeDao.loadIntoDW() && this.provinceDao.loadIntoDW()
					&& this.lottoDao.loadIntoDW();
		}
		return false;
	}
	
//	public boolean loadDataToDWAllTable() throws ClassNotFoundException, SQLException {
//		ArrayList<Table> tables = this.getNameTables();
//		int amountTableEffect = 0;
//		for (Table table : tables) {
//			int rowEffect = this.loadDataToDWOneTable(table.getId(), table.getId(), "data_warehouse");
//			if (this.checkEqualRowInsert(rowEffect, this.getNumberRow(table.getName()))) {
//				amountTableEffect++;
//			}
//		}
//		return tables.size() == amountTableEffect;
//	}
//	
//	public boolean loadDataToDWAllTable() throws ClassNotFoundException, SQLException {
//		ArrayList<Table> tables = this.getNameTables();
//		int amountTableEffect = 0;
//		for (Table table : tables) {
//			int rowEffect = this.loadDataToDWOneTable(table.getId(), table.getId(), "data_warehouse");
//			if (this.checkEqualRowInsert(rowEffect, this.getNumberRow(table.getName()))) {
//				amountTableEffect++;
//			}
//		}
//		return tables.size() == amountTableEffect;
//	}
//	
//	public String getNumberRow(String name_table) {
//		String output = "";
//		switch (name_table) {
//		case "PROVINCE":
//			output = QUERIES.PROVINCE.GET_NUMBER_ROW;
//			break;
//		case "PRIZE":
//			output = QUERIES.PRIZE.GET_NUMBER_ROW;
//			break;
//		case "LOTTO":
//			output = QUERIES.LOTTO.GET_NUMBER_ROW;
//			break;
//		case "DATE_DIM":
//			output = QUERIES.DATE_DIM.GET_NUMBER_ROW;
//			break;
//		}
//		return output;
//	}
	
	public ArrayList<Table> getNameTables() throws ClassNotFoundException, SQLException {
		ArrayList<Table> output = new ArrayList<>();
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.TABLES.GET_ALL);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			output.add(new Table(rs.getString("id_table"), rs.getString("name_table")));
		}
		return output;
	}
	
	public int loadDataToDWOneTable(String tableOut, String tableIn, String databaseName)
			throws SQLException, ClassNotFoundException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.LOAD_DATA);
		ps.setString(1, tableOut);
		ps.setString(2, tableIn);
		ps.setString(3, databaseName);
		System.out.println(ps.executeQuery());
//		ResultSet rs = ps.executeQuery();
//		System.out.println(rs.getString(1));
		return ps.executeUpdate();

	}
	
	public boolean setStatusLog(String statusTarget) throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.SET_STATUS);
		ps.setString(1, statusTarget);
		return ps.executeUpdate() == 1;
	}

	public String getStatusLog() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.GET_STATUS_LOG);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}

}

package script3;

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

	public MyMain() throws SQLException {
		// TODO Auto-generated constructor stub
		dcon = new DatabaseConnection();

	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		MyMain main = new MyMain();
		main.transformStagingToDW();

	}

	public void loadDataIntoTable(String url, String query) throws SQLException, ClassNotFoundException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, url);
		ps.executeUpdate();
	}

	public int loadDataToDWOneTable(String tableOut, String tableIn, String databaseName)
			throws SQLException, ClassNotFoundException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.LOAD_DATA);
		ps.setString(1, tableOut);
		ps.setString(2, tableIn);
		ps.setString(3, databaseName);
		return ps.executeUpdate();

	}

	public boolean checkEqualRowInsert(int numberInserted, String query) throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return numberInserted == rs.getInt(1);
	}

	public boolean loadDataToDWAllTable() throws ClassNotFoundException, SQLException {
		ArrayList<Table> tables = this.getNameTables();
		int amountTableEffect = 0;
		this.deleteDataDateDim();
		this.deleteDataPrize();
		for (Table table : tables) {
			int rowEffect = this.loadDataToDWOneTable(table.getId(), table.getId(), "data_warehouse");
			if (this.checkEqualRowInsert(rowEffect, this.getNumberRow(table.getName()))) {
				amountTableEffect++;
			}
		}
		return tables.size() == amountTableEffect;
	}

	public boolean setStatusLog(String statusTarget) throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.SET_STATUS);
		ps.setString(1, statusTarget);
		return ps.executeUpdate() == 1;
	}

	public void deleteAllDataStaging() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.DELELE_ALL_DATA);
		ps.executeUpdate();
	}
	
	public void deleteDataDateDim() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.DW_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.DELETE_DATA_DATE_DIM_DW);
		ps.executeUpdate();
	}
	
	public void deleteDataPrize() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.DW_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.DELETE_DATA_PRIZE_DW);
		ps.executeUpdate();
	}

	public void transformStagingToDW() throws ClassNotFoundException, SQLException {
		if (this.loadDataToDWAllTable()) {
			this.deleteAllDataStaging();
			this.setStatusLog("FI");
		}
	}

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

	public String getNumberRow(String name_table) {
		String output = "";
		switch (name_table) {
		case "PROVINCE":
			output = QUERIES.PROVINCE.GET_NUMBER_ROW;
			break;
		case "PRIZE":
			output = QUERIES.PRIZE.GET_NUMBER_ROW;
			break;
		case "LOTTO":
			output = QUERIES.LOTTO.GET_NUMBER_ROW;
			break;
		case "DATE_DIM":
			output = QUERIES.DATE_DIM.GET_NUMBER_ROW;
			break;
		}
		return output;
	}

	

}

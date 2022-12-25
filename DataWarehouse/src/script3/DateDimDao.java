package script3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class DateDimDao {
	DatabaseConnection dcon;
	Connection conn;

	public DateDimDao(DatabaseConnection dcon) throws ClassNotFoundException, SQLException {
		this.dcon = dcon;
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
	}
	/*
	 * LOAD DATA INTO DATA WEREHOUSE
	 */
	public boolean loadIntoDW() throws ClassNotFoundException, SQLException {
		int count = 0;
		ArrayList<Integer> idList = this.getDiff();
		for (Integer id : idList) {
			if (this.save(id)) {
				count++;
			}
		}
		return count == idList.size();
	}

	/*
	 * GET DATE DIM
	 */
	public ArrayList<Integer> getDiff() throws ClassNotFoundException, SQLException {
		ArrayList<Integer> output = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(QUERIES.DATE_DIM.GET_DIFF);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			output.add(rs.getInt(1));
		}
		return output;
	}
	/*
	 * CHECK ID  isEXITS 
	 */
	public boolean checkExistId(int idTarget) throws ClassNotFoundException, SQLException {
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.DATE_DIM.GET_BY_ID_DW);
		ps.setInt(1, idTarget);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	public boolean save(int idTarget) throws ClassNotFoundException, SQLException {
		boolean output = false;
		if (checkExistId(idTarget)) {
			output = update(idTarget);
		} else {
			output = insert(idTarget);
		}
		return output;
	}
	
	/*
	 * UPDATE DATE DIM
	 */
	public boolean update(int idTarget) throws ClassNotFoundException, SQLException {
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.DATE_DIM.UPDATE);
		ps.setInt(1, idTarget);
		int result = ps.executeUpdate();
		return result == 1;
	}
	
	/*
	 * INSER ALL DATE DIM FOR STAGING
	 */
	public boolean insert(int idTarget) throws ClassNotFoundException, SQLException {
		
		PreparedStatement ps = conn.prepareStatement(QUERIES.DATE_DIM.INSERT_ALL_FROM_STAGING);
		ps.setInt(1, idTarget);
		int result = ps.executeUpdate();
		return result == 1;
	}

}

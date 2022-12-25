package script3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class LottoDao {
	DatabaseConnection dcon;
	Connection conn;
	public LottoDao(DatabaseConnection dcon) throws ClassNotFoundException, SQLException {
		this.dcon = dcon;
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
	}
	/*
	 * LOAD DATA INTO DATA WEREHOUSE
	 */
	public boolean loadIntoDW() throws ClassNotFoundException, SQLException {
		int count = 0;
		ArrayList<String> idList = this.getDiff();
		for(String id: idList) {
			if(this.save(id)) {
				count++;
			}
		}
		return count == idList.size();
	}
	
	/*
	 * GET DATE DIM
	 */
	public ArrayList<String> getDiff() throws ClassNotFoundException, SQLException {
		ArrayList<String> output = new ArrayList<>();
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOTTO.GET_DIFF);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			output.add(rs.getString(1));
		}
		return output;
	}
	/*
	 * CHECK ID  isEXIT 
	 */
	public boolean checkExistId(String idTarget) throws ClassNotFoundException, SQLException {
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOTTO.GET_BY_ID_DW);
		ps.setString(1, idTarget);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	/*
	 * CHECK ID AND INSERT OR UPDATE ALL DATE DIM STAGING 
	 */
	public boolean save(String idTarget) throws ClassNotFoundException, SQLException {
		boolean output = false;
		if (checkExistId(idTarget)) {
			output = update(idTarget);
		} else {
			output = insert(idTarget);
		}
		return output;
	}
	
	
	/*
	 * UPDATE LOTTO
	 */
	public boolean update(String idTarget) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOTTO.UPDATE);
			ps.setString(1, idTarget);
		int result = ps.executeUpdate();
		return result == 1;
	}
	
	/*
	 * INSER ALL DATE DIM FOR STAGING
	 */
	public boolean insert(String idTarget) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOTTO.INSERT_ALL_FROM_STAGING);
		ps.setString(1, idTarget);
		int result = ps.executeUpdate();
		return result == 1;
	}
}

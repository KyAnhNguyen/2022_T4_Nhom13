package script3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class PrizeDao {
	DatabaseConnection dcon;
	Connection conn;
	public PrizeDao(DatabaseConnection dcon) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		this.dcon = dcon;
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
	}
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

	public ArrayList<String> getDiff() throws ClassNotFoundException, SQLException {
		ArrayList<String> output = new ArrayList<>();
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.PRIZE.GET_DIFF);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			output.add(rs.getString(1));
		}
		return output;
	}
	
	public boolean checkExistId(String idTarget) throws ClassNotFoundException, SQLException {
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.PRIZE.GET_BY_ID_DW);
		ps.setString(1, idTarget);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	public boolean save(String idTarget) throws ClassNotFoundException, SQLException {
		boolean output = false;
		if (checkExistId(idTarget)) {
			output = update(idTarget);
		} else {
			output = insert(idTarget);
		}
		return output;
	}
	
	
	public boolean update(String idTarget) throws ClassNotFoundException, SQLException {
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.PRIZE.UPDATE);
			ps.setString(1, idTarget);
		int result = ps.executeUpdate();
		return result == 1;
	}
	
	public boolean insert(String idTarget) throws ClassNotFoundException, SQLException {
//		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.PRIZE.INSERT_ALL_FROM_STAGING);
		ps.setString(1, idTarget);
		int result = ps.executeUpdate();
		return result == 1;
	}
}

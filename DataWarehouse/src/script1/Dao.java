package script1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class Dao {
	DatabaseConnection dcon;
	Connection conn;

	public static Dao instance;

	public Dao() {
		dcon = new DatabaseConnection();
	}

	// Kiểm tra đã có dòng log của ngày hôm nay không?
	public boolean hasLog() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.GET_LOG);
		ResultSet rs = ps.executeQuery();
		int count = 0;
		while (rs.next()) {
			count++;
			if (count == 2) {
				return true;
			}
		}
		return false;
	}

	// Lấy ra danh sách config
	public List<Config> getConfig() throws ClassNotFoundException, SQLException {
		List<Config> re = new ArrayList<>();
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.CONFIG.GET_CONFIG);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			re.add(new Config(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getDate(8), rs.getDate(9)));
		}
		return re;
	}

	// Lấy ra servername của config
	public String getServerName() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.CONFIG.GET_DATABASE_STAGING);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return rs.getString(1);
		return "";
	}

	// Thêm 1 dòng log
	public boolean addLog(int id_config, String status, int id_contactor, Date created_date, Date updated_date)
			throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.ADD_LOG);
		ps.setInt(1, id_config);
		ps.setString(2, status);
		ps.setInt(3, id_contactor);
		ps.setDate(4, created_date);
		ps.setDate(5, updated_date);
		if (ps.executeUpdate() > 0)
			return true;
		return false;
	}

	// Trả về status của log hiện tại
	public String getStatusLog() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.GET_STATUS_LOG);
		ResultSet rs = ps.executeQuery();
		String check = "";
		while (rs.next()) {
			check += rs.getString(1);
		}
		return check;
	}

	// Set status của log
	public boolean setStatusLog(String statusTarget) throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.LOG.SET_STATUS);
		ps.setString(1, statusTarget);
		return ps.executeUpdate() == 1;
	}

	// Trả về status của log hiện tại
	public int getIdContactor(String userName) throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.CONTROLLER_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.CONTACTOR.GET_ID_CONTACTOR);
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		return 0;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Dao dao = new Dao();
		System.out.println(dao.getStatusLog());
	}
}

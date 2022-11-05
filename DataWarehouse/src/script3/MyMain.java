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
			this.deleteAllDataStaging();
			this.setStatusLog("FI");
			this.conn.close();
			return true;
		}
		return false;
	}

	public boolean checkLoad() throws ClassNotFoundException, SQLException {
		if (this.getStatusLog().equals("SU")) {
			System.out.println("load");
			return this.dateDimDao.loadIntoDW() && this.prizeDao.loadIntoDW() && this.provinceDao.loadIntoDW()
					&& this.lottoDao.loadIntoDW();
		}
		return false;
	}
	
	public boolean deleteAllDataStaging() throws ClassNotFoundException, SQLException {
		conn = dcon.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(QUERIES.QueryTransformStaging.DELELE_ALL_DATA);
		return ps.executeUpdate() == 1;
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
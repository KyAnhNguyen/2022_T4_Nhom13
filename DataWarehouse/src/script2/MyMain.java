package script2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import utils.DatabaseAttributes;
import utils.DatabaseConnection;
import utils.QUERIES;

public class MyMain {
	DatabaseConnection dbConn;
	Connection conn;
	public static String path_date_dim_download, path_date_dim_upload;

	public MyMain() throws Exception, SQLException {
		dbConn = new DatabaseConnection();

		String[] arr_log = getConfig(QUERIES.QueryController.GET_CONFIG, 1).split("&");

		Config.SERVER = arr_log[1];
		Config.USERNAME = arr_log[3];
		Config.PASSWORD = arr_log[4];

		Handle_files.file_path_upload = arr_log[5] + java.time.LocalDate.now() + "\\";
		Handle_files.file_path_download = arr_log[6] + java.time.LocalDate.now() + "\\";
		path_date_dim_upload = arr_log[5];
		path_date_dim_download = arr_log[6];

//		System.out.println(Arrays.toString(arr_log));
//		System.out.println(Config.SERVER);
//		System.out.println(Config.USERNAME);
//		System.out.println(Config.PASSWORD);
//		System.out.println(Handle_files.file_path_upload);
//		System.out.println(Handle_files.file_path_download);
	}

	public String getConfig(String query, int id) throws ClassNotFoundException, SQLException {

		String result = "";

		this.conn = this.dbConn.connect(DatabaseAttributes.CONTROLLER_DATABASE);

		PreparedStatement ps = this.conn.prepareStatement(query);

		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			result += rs.getInt("id_config") + "&";
			result += rs.getString("server_name") + "&";
			result += rs.getString("url") + "&";
			result += rs.getString("username") + "&";
			result += rs.getString("password") + "&";
			result += rs.getString("path_upload") + "&";
			result += rs.getString("path_download");
		}
		return result;
	}

	public void loadDataIntoTable(String url, String query) throws SQLException, ClassNotFoundException {
		conn = dbConn.connect(DatabaseAttributes.STAGING_DATABASE);
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, url);
		ps.executeUpdate();
	}

	public String getLog(String query, String curren_date) throws ClassNotFoundException, SQLException {

		String result = "";

		this.conn = this.dbConn.connect(DatabaseAttributes.CONTROLLER_DATABASE);

		PreparedStatement ps = this.conn.prepareStatement(query);

		ps.setString(1, curren_date);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			result += rs.getInt("id_log") + "-";
			result += rs.getInt("id_config") + "-";
			result += rs.getString("status") + "-";
			result += rs.getInt("id_contactor") + "-";
		}
		return result;
	}

	public boolean update_log_status(String query, String status, String curren_date)
			throws ClassNotFoundException, SQLException {

		this.conn = this.dbConn.connect(DatabaseAttributes.CONTROLLER_DATABASE);

		PreparedStatement ps = this.conn.prepareStatement(query);

		ps.setString(1, status);

		ps.setString(2, curren_date);

		ps.executeUpdate();

		return true;
	}

	public static boolean check_upload(String name_file_lotto, String name_file_prize, String name_file_province) {
		if (Handle_files.upload_file(name_file_province)) {
			if (Handle_files.upload_file(name_file_prize)) {
				if (Handle_files.upload_file(name_file_lotto)) {
					return true;
				} else {
					Handle_files.delete_file(name_file_province);
					Handle_files.delete_file(name_file_prize);
					return false;
				}
			} else {
				Handle_files.delete_file(name_file_province);
				return false;
			}
		}
		return false;
	}

	public static String log_status(String log) {
		String[] arr_log = log.split("-");
		return arr_log[2];
	}

	public static boolean check_log_status(String status, String condition) {
		return status.equals(condition.toUpperCase()) ? true : false;
	}



	public static void main(String[] args) throws Exception {
		MyMain mm = new MyMain();

		String date_current = java.time.LocalDate.now() + "";

//		 Step: Check the status of the log
		if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG, date_current)), "ER")) {

//			Step: upload file extract and check
			if (check_upload("lotto", "prize", "province")) {

//				Step: updated status of log -> UPFI and check update
				if (mm.update_log_status(QUERIES.QueryController.UPDATE_LOG_STATUS, "UPFI", date_current)) {

//					Step: check status of log
					if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG, date_current)),
							"UPFI")) {
						Handle_files.download_file("lotto");
						Handle_files.download_file("prize");
						Handle_files.download_file("province");

//						Step: updated status of log -> SAVE
						if (mm.update_log_status(QUERIES.QueryController.UPDATE_LOG_STATUS, "SAVE", date_current)) {

//							Step: check status of log
							if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG, date_current)),
									"SAVE")) {
//								load data date_dim
								mm.loadDataIntoTable(path_date_dim_download + "date_dim_without_quarter.csv",
										QUERIES.QueryTransformCSV.DATE_DIM);
								mm.loadDataIntoTable(Handle_files.file_path_download + "province.csv",
										QUERIES.QueryTransformCSV.PROVINCE);
								mm.loadDataIntoTable(Handle_files.file_path_download + "prize.csv",
										QUERIES.QueryTransformCSV.PRIZE);
								
//								bug....
//								mm.loadDataIntoTable(Handle_files.file_path_download + "lotto.csv",
//										QUERIES.QueryTransformCSV.LOTTO);
								System.out.println("load success");
							} else {
								System.out.println("Script 2: not same SAVE && stop program");
								System.exit(0);
							}
						} else {
							System.out.println("updated status failed");
//							????
						}
					} else {
						if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG, date_current)),
								"SAVE")) {
//							load data staging
						} else {
							System.out.println("Script 2: not same SAVE && stop program");
							System.exit(0);
						}
					}
				}
			} else {
				System.out.println("Error : updated status of log -> UPFI and check update");
				System.out.println("Script 2: upload file failed && stop program");
				System.exit(0);
			}
		} else {
			System.out.println("Error: oh no script 1");
			System.exit(0);
		}
	}
}

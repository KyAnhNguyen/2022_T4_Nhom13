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

	public MyMain() throws Exception, SQLException {
		dbConn = new DatabaseConnection();

		String[] arr_log = getConfig(QUERIES.QueryController.GET_CONFIG, 1).split("&");

		Config.SERVER = arr_log[1];
		Config.USERNAME = arr_log[3];
		Config.PASSWORD = arr_log[4];

		Handle_files.file_path_upload = arr_log[5] + java.time.LocalDate.now() + "\\";
		Handle_files.file_path_download = arr_log[6] + java.time.LocalDate.now() + "\\";
	}

	/*
	 * GET CONFIG FROM TABLE CONFIG OF DATABASE CONTROLLER
	 */
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

	/*
	 * LOAD DATA FROM FILE.CSV INTO TABLE
	 */
	public void loadDataIntoTable(String url, String query) throws SQLException, ClassNotFoundException {
		conn = dbConn.connect(DatabaseAttributes.STAGING_DATABASE);
		
		PreparedStatement ps = conn.prepareStatement(query);
		
		ps.setString(1, url);
		
		ps.executeUpdate();
	}

	/*
	 * GET LOG BY DATE CURRENT
	 */
	public String getLog(String query) throws ClassNotFoundException, SQLException {

		String result = "";

		this.conn = this.dbConn.connect(DatabaseAttributes.CONTROLLER_DATABASE);

		PreparedStatement ps = this.conn.prepareStatement(query);

//		ps.setString(1, curren_date);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			result += rs.getInt("id_log") + "-";
			result += rs.getInt("id_config") + "-";
			result += rs.getString("status") + "-";
			result += rs.getInt("id_contactor") + "-";
		}
		return result;
	}

	/*
	 * UPDATE STATUS OF LOG
	 */
	public boolean update_log_status(String query, String status, String curren_date)
			throws ClassNotFoundException, SQLException {

		this.conn = this.dbConn.connect(DatabaseAttributes.CONTROLLER_DATABASE);

		PreparedStatement ps = this.conn.prepareStatement(query);

		ps.setString(1, status);

		ps.setString(2, curren_date);

		ps.executeUpdate();

		return true;
	}

	/*
	 * LOAD FILE TO FTP SERVER AND CHECK UPLOAD
	 */
	public static boolean check_upload(String name_file_lotto, String name_file_prize, String name_file_province,String date_dim) {
//		CHECK UPLOAD FILE DATE_DIM TO FTP
		if (Handle_files.upload_file(date_dim)) {
			System.out.println("Load date_dim success");
//			CHECK UPLOAD FILE PROVINCE TO FTP
			if (Handle_files.upload_file(name_file_province)) {
				System.out.println("Load province success");
//				CHECK UPLOAD FILE PRIZE TO FTP
				if (Handle_files.upload_file(name_file_prize)) {
					System.out.println("Load prize success");
//					CHECK UPLOAD FILE LOTTO TO FTP
					if (Handle_files.upload_file(name_file_lotto)) {
						System.out.println("Load lotto success");
						return true;
					} else {
//						DELETE FILE UPLOAD BEFORE 
						Handle_files.delete_file(date_dim);
						Handle_files.delete_file(name_file_province);
						Handle_files.delete_file(name_file_prize);
						System.out.println("Load lotto fail");
						return false;
					}
				} else {
//					DELETE FILE UPLOAD BEFORE 
					Handle_files.delete_file(date_dim);
					Handle_files.delete_file(name_file_province);
					System.out.println("Load prize fail");
					return false;
				}
			} else {
//				DELETE FILE UPLOAD BEFORE 
				Handle_files.delete_file(date_dim);
				System.out.println("Load province fail");
				return false;
			}
		}
		System.out.println("Load date_dim fail");
		return false;
	}

	/*
	 * GET STATUS OF LOG
	 */
	public static String log_status(String log) {
		String[] arr_log = log.split("-");
		return arr_log[2];
	}

	/*
	 * COMPARE STATUS -> TRUE : FALSE;
	 */
	public static boolean check_log_status(String status, String condition) {
		return status.equals(condition.toUpperCase()) ? true : false;
	}
	
	/*
	 * LOAD DATA INTO STATGING
	 */
	private static void push_staging(MyMain mm, String date_current) throws ClassNotFoundException, SQLException {
		mm.loadDataIntoTable(Handle_files.file_path_download + "date_dim_without_quarter.csv", QUERIES.QueryTransformCSV.DATE_DIM);
		mm.loadDataIntoTable(Handle_files.file_path_download + "province.csv", QUERIES.QueryTransformCSV.PROVINCE);
		mm.loadDataIntoTable(Handle_files.file_path_download + "prize.csv", QUERIES.QueryTransformCSV.PRIZE);
		mm.loadDataIntoTable(Handle_files.file_path_download + "lotto.csv", QUERIES.QueryTransformCSV.LOTTO);
		if (mm.update_log_status(QUERIES.QueryController.UPDATE_LOG_STATUS, "SU", date_current)) {
			System.out.println("Done: script 2");
			System.exit(0);
		}

	}
	
	
	/*
	 * DOWNLOAD FILE FROM SERVER
	 */
	private static void download() {
		Handle_files.download_file("lotto");
		Handle_files.download_file("prize");
		Handle_files.download_file("province");
		Handle_files.download_file("date_dim_without_quarter");
	}

	public static void main(String[] args) throws Exception {
		MyMain mm = new MyMain();
//		DATE CURRENT 
		String date_current = java.time.LocalDate.now() + "";
//		CHECK STATUS OF LOG
		if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG)), "ER")) {
//			UPLOAD FILE EXTRACT AND CHECK
			if (check_upload("lotto", "prize", "province", "date_dim_without_quarter")) {
//				UPDATED STATUS OF LOG -> UPFI AND CHECK UPDATE
				if (mm.update_log_status(QUERIES.QueryController.UPDATE_LOG_STATUS, "UPFI", date_current)) {
//					CHECK STATUS OF LOG
					if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG)),"UPFI")) {
//						DOWNLOAD FILE FROM SERVER
						download();
//						UPDATED STATUS OF LOG -> SAVE
						if (mm.update_log_status(QUERIES.QueryController.UPDATE_LOG_STATUS, "SAVE", date_current)) {
//							CHECK STATUS OF LOG
							if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG)),"SAVE")) {
//								LOAD DATA INTO STATGING
								push_staging(mm, date_current);
							} else {
								System.out.println("Script 2: not same SAVE && stop program");
								System.exit(0);
							}
						} else {
							System.out.println("updated status failed");
//							????
						}
					} else {
						if (check_log_status(log_status(mm.getLog(QUERIES.QueryController.GET_LOG)),"SAVE")) {
//							LOAD DATA INTO STATGING
							push_staging(mm, date_current);
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

package utils;

public class QUERIES {

	// csv to staging
	public static class QueryTransformCSV {
		public static final String DATE_DIM = "LOAD DATA INFILE ? INTO TABLE date_dim FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n'";
		public static final String LOTTO = "LOAD DATA INFILE ? \n" + "INTO TABLE lotto \n"
				+ "FIELDS TERMINATED BY ','\n" + "ENCLOSED BY '\"'\n" + "LINES TERMINATED BY '\r\n'\n"
				+ "IGNORE 1 ROWS \n"
				+ "(@natural_key, id_province, id_prize, number, status, @created_date, @updated_date)\n"
				+ "SET natural_key = CONVERT(@natural_key, INT) ,\n"
				+ "created_date = (SELECT date_sk FROM date_dim WHERE full_date = DATE(NOW())) ,\n"
				+ "updated_date = STR_TO_DATE(@updated_date, '%Y-%m-%d');";
		public static final String PROVINCE = "LOAD DATA INFILE  ?  INTO TABLE province FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 ROWS "
				+ "(id_province, name_province, @created_date, @updated_date) "
				+ "SET created_date = STR_TO_DATE(@created_date, '%Y-%m-%d'),"
				+ "updated_date = STR_TO_DATE(@updated_date, '%Y-%m-%d');";
		public static final String PRIZE = "LOAD DATA INFILE ? \n" + "\tINTO TABLE prize\n"
				+ "\tFIELDS TERMINATED BY ','\n" + "\tENCLOSED BY '\"'\n" + "\tLINES TERMINATED BY '\\n'\n"
				+ "    IGNORE 1 ROWS\n" + "(id_prize, name_prize, value_prize, @created_date, @updated_date)\n"
				+ "SET created_date = STR_TO_DATE(@created_date, '%Y-%m-%d'),\n"
				+ "updated_date = STR_TO_DATE(@updated_date, '%Y-%m-%d');";
	}

	// staging to data warehouse
	public static class QueryTransformStaging {
		public static final String LOAD_DATA = "call load_data_into_dw(?, ?, ?, @output);\r\n";
		public static final String DELELE_ALL_DATA = "call delete_all_date_from_table()";
	}

	public static class PROVINCE {
		public static final String DELETE_ALL = "call delete_all_province";
		public static final String GET_NUMBER_ROW = "select get_number_row_province()";
	}
	public static class PRIZE {
		public static final String DELETE_ALL = "call delete_all_prize";
		public static final String GET_NUMBER_ROW = "select get_number_row_prize()";
	}
	public static class LOTTO {
		public static final String DELETE_ALL = "call delete_all_lotto";
		public static final String GET_NUMBER_ROW = "select get_number_row_lotto()";
	}
	public static class DATE_DIM {
		public static final String DELETE_ALL = "call delete_all_date_dim";
		public static final String GET_NUMBER_ROW = "select get_number_row_date_dim()";
	}

	public static class TABLES {
		public static final String GET_ALL = "SELECT id_table, name_table FROM tables;";
	}

	public static class LOG {
		public static final String SET_STATUS = "call set_status_log(?);";
	}

	public static class CONFIG {
		public static final String GET_DATABASE_STAGING = "select url from config where server_name = 'mysql' ";
	}
	
	public static class QueryController {
		
		public static final String INSERT_LOG = "";

		public static final String GET_LOG = "SELECT * FROM log WHERE created_date = ?";

		public static final String UPDATE_LOG_STATUS = "UPDATE log SET status = ? where created_date = ?";
		
		public static final String GET_CONFIG = "SELECT * FROM config WHERE id_config = ?";

	}
}

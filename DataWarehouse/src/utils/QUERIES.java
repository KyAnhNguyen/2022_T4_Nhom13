package utils;

public class QUERIES {

	// csv to staging
	public static class QueryTransformCSV {
		public static final String DATE_DIM = "LOAD DATA INFILE ? INTO TABLE date_dim FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n'";
		public static final String LOTTO = "LOAD DATA INFILE ? \r\n" + "INTO TABLE lotto\r\n"
				+ "FIELDS TERMINATED BY ','\r\n" + "ENCLOSED BY '\"'\r\n" + "LINES TERMINATED BY '\\n'\r\n"
				+ "IGNORE 1 ROWS\r\n"
				+ "(natural_key, id_province, id_prize, number, status, created_date, @updated_date)\r\n"
				+ "SET updated_date = STR_TO_DATE(@updated_date, '%Y-%m-%d');";
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
		public static final String DELETE_DATA_DATE_DIM_DW = "call delete_all_date_dim()";
		public static final String DELETE_DATA_PRIZE_DW = "call delete_all_prize()";
	}

	public static class PROVINCE {
		public static final String GET_DIFF = "call get_diff_province;";
		public static final String GET_BY_ID_DW = "select * from data_warehouse.province where id_province = ?";
		public static final String UPDATE = "call p_update_province_dw(?)";
		public static final String INSERT_ALL_FROM_STAGING = "call p_insert_province_dw(?)";
	}

	public static class PRIZE {
		public static final String GET_DIFF = "call get_diff_prize;";
		public static final String GET_BY_ID_DW = "select * from data_warehouse.prize where id_prize = ?";
		public static final String UPDATE = "call p_update_prize_dw(?)";
		public static final String INSERT_ALL_FROM_STAGING = "call p_insert_prize_dw(?)";
	}

	public static class LOTTO {
		public static final String GET_DIFF = "call get_diff_lotto;";
		public static final String GET_BY_ID_DW = "select * from data_warehouse.lotto where natural_key = ?";
		public static final String UPDATE = "call p_update_lotto_dw(?)";
		public static final String INSERT_ALL_FROM_STAGING = "call p_insert_lotto_dw(?)";
	}

	public static class DATE_DIM {
		public static final String GET_DIFF = "call get_diff_test_date_dim;";
		public static final String GET_BY_ID_DW = "select * from data_warehouse.date_dim where date_sk = ?";
		public static final String UPDATE = "call p_update_date_dim_dw(?)";
		public static final String INSERT_ALL_FROM_STAGING = "call p_insert_date_dim_dw(?)";
	}

	public static class TABLES {
		public static final String GET_ALL = "SELECT id_table, name_table FROM tables ORDER BY created_date ASC;";
	}

	public static class LOG {
		public static final String SET_STATUS = "call set_status_log(?);";
		public static final String GET_LOG = "call get_log_today();";
		public static final String ADD_LOG = "call add_log(?, ?, ?, ?, ?);";
		public static final String GET_STATUS_LOG = "call get_status_log();";
	}

	public static class CONFIG {
		public static final String GET_DATABASE_STAGING = "select url from config where server_name = 'mysql' ";
		public static final String GET_CONFIG = "call get_config;";
	}

	public static class QueryController {

		public static final String INSERT_LOG = "";

//		public static final String GET_LOG = "SELECT * FROM log WHERE created_date = Date(Now())";

//		public static final String UPDATE_LOG_STATUS = "UPDATE log SET status = ? where created_date = ?";

//		public static final String GET_CONFIG = "SELECT * FROM config WHERE id_config = ?";

	}

	public static class CONTACTOR {
		public static final String GET_ID_CONTACTOR = "call get_id_contactor(?);";
	}
}

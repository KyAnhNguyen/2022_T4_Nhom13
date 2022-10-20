package utils;

public class QUERIES {

	// csv to staging
	public static class QueryTransformCSV {
		public static final String LOAD_DATA = "";
		public static final String DATE_DIM = "LOAD DATA INFILE ? INTO TABLE date_dim FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n'";
		public static final String LOTTO = "";
		public static final String PROVINCE = "LOAD DATA INFILE  ?  INTO TABLE province FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 ROWS "
				+ "(id_province, name_province, @created_date, @updated_date) "
				+ "SET created_date = STR_TO_DATE(@created_date, '%Y-%m-%d'),"
				+ "updated_date = STR_TO_DATE(@updated_date, '%Y-%m-%d');";
		public static final String PRIZE = "LOAD DATA INFILE ? \n" +
				"\tINTO TABLE prize\n" +
				"\tFIELDS TERMINATED BY ','\n" +
				"\tENCLOSED BY '\"'\n" +
				"\tLINES TERMINATED BY '\\n'\n" +
				"    IGNORE 1 ROWS\n" +
				"(id_prize, name_prize, value_prize, @created_date, @updated_date)\n" +
				"SET created_date = STR_TO_DATE(@created_date, '%Y-%m-%d'),\n" +
				"updated_date = STR_TO_DATE(@updated_date, '%Y-%m-%d');";
	}

	// staging to data warehouse
	public static class QueryTransformStaging {
		public static final String LOTTO = "";
		public static final String PROVINCE = "";
		public static final String PRIZE = "";
		public static final String DATE_DIM = "";
	}

	public static class QueryController {
		public static final String INSERT_LOG = "";
	}

	public static class Staging {

	}

	public static class QueryConfig {
		public static final String GET_DATABASE_STAGING = "select url from config where server_name = 'mysql' ";
	}
}

package utils;

public class QUERIES {

	// csv to staging
	public static class QueryTransformCSV {
		public static final String LOAD_DATA = ""
				+ "LOAD DATA LOCAL INFILE ?\r\n"
				+ "	INTO TABLE ?\r\n"
				+ "	FIELDS TERMINATED BY ','\r\n"
				+ "	ENCLOSED BY '\"'\r\n"
				+ "	LINES TERMINATED BY '\\n'";
				
	}

	// staging to data warehouse
	public static class QueryTransformStaging {
		public static final String LOTTO = "";
		public static final String PROVINCE = "";
		public static final String PRIZE = "";
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

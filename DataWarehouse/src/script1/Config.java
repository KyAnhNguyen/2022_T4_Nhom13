package script1;

import java.sql.Date;

public class Config {
	private int id_config;
	private String server_name;
	private String url;
	private String username;
	private String password;
	private String path_upload;
	private String path_download;
	private Date created_date;
	private Date updated_date;

	public Config(int id_config, String server_name, String url, String username, String password, String path_upload,
			String path_download, Date created_date, Date updated_date) {
		super();
		this.id_config = id_config;
		this.server_name = server_name;
		this.url = url;
		this.username = username;
		this.password = password;
		this.path_upload = path_upload;
		this.path_download = path_download;
		this.created_date = created_date;
		this.updated_date = updated_date;
	}

	public int getId_config() {
		return id_config;
	}

	public String getServer_name() {
		return server_name;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPath_upload() {
		return path_upload;
	}

	public String getPath_download() {
		return path_download;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}
}

package script1;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Province {
	private String id_province;
	private String name_province;
	private String created_date;
	private String updated_date;

	public Province(String id_province, String name_province, String created_date, String updated_date) {
		super();
		this.id_province = id_province;
		this.name_province = name_province;
		this.created_date = created_date;
		this.updated_date = updated_date;
	}

	public void writeData(File file, OutputStreamWriter osw) {
		try {
			osw.write("\ufeff" + toString());
			osw.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return id_province + "," + name_province + "," + created_date + "," + updated_date;
	}

}

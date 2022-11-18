package script1;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

public class Lotto {
	private String id_province;
	private String id_prize;
	private String number;
	private String status;
	private int created_date;
	private String updated_date;

	public Lotto(String id_province, String id_prize, String number, String status, int created_date,
			String updated_date) {
		super();
		this.id_province = id_province;
		this.id_prize = id_prize;
		this.number = number;
		this.status = status;
		this.created_date = created_date;
		this.updated_date = updated_date;
	}

	@Override
	public String toString() {
		return id_province + "," + id_prize + "," + "\"\t" + number + "\"" + "," + status + "," + created_date + "," + updated_date;
	}

	public void writeData(File file, OutputStreamWriter osw) {
		String uniqueID = UUID.randomUUID().toString();
		try {
			osw.write("\ufeff" + uniqueID + "," + toString());
			osw.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

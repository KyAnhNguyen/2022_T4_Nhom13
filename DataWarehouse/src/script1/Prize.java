package script1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Prize {
	private String id_prize;
	private String name_prize;
	private int value_prize;
	private String created_date;
	private String updated_date;

	public Prize(String id_prize, String name_prize, int value_prize, String created_date, String updated_date) {
		super();
		this.id_prize = id_prize;
		this.name_prize = name_prize;
		this.value_prize = value_prize;
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
		return id_prize + "," + name_prize + "," + value_prize + "," + created_date + "," + updated_date;
	}

}

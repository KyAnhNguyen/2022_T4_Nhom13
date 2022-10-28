package script1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class PrizeData {
	private static Dao dao = new Dao();
	// Lấy ra bảng xổ số ở trên web (https://www.minhngoc.net.vn/doi-so-trung.html)
	public static List<List<String>> getTable() throws IOException, ClassNotFoundException, SQLException {
		Document d = Jsoup.connect(dao.getConfig().get(1).getUrl()).timeout(6000).get();
		Elements ele = d.select("div.box_kqxs");

		Elements content = ele.get(0).select("div.content table.bkqmiennam > tbody > tr");

		Elements td = content.get(0).select("> td");
		Elements td1 = td.get(0).select("tr");

		List<List<String>> table = new ArrayList<>();

		List<String> col1 = new ArrayList<>();
		for (int i = 0; i < td1.size(); i++) {
			if (i == 0 || i == 1) {
				col1.add(td1.get(i).text());
			} else {
				col1.add(td1.get(i).select(">td").attr("class"));
			}
		}
		table.add(col1);

		List<String> col2 = new ArrayList<>();
		for (int i = 0; i < td1.size(); i++) {
			col2.add(td1.get(i).text());
		}
		table.add(col2);

		Elements td2 = td.get(1).select("table.rightcl");

		Elements col = td2.get(3).select("tr");
		ArrayList<String> strList = new ArrayList<>();
		for (Element e : col) {
			strList.add(e.text());
		}
		table.add(strList);
		return table;
	}
    // Lấy ra danh sách giải thưởng 
	public static List<Prize> getAllPrize(List<List<String>> table) {
		List<Prize> re = new ArrayList<>();
		String current = table.get(0).get(1);
		for (int j = 2; j < table.get(0).size(); j++) {
			String id_prize = table.get(0).get(j);
			String name_prize = table.get(1).get(j);
			String value_prize = table.get(2).get(j).replaceAll(",", "");
			String[] split = current.split("/");
			re.add(new Prize(id_prize, name_prize, Integer.parseInt(value_prize), split[2] + "-" + split[1] + "-" + split[0], "9999-12-31"));
		}
		return re;
	}
	// Ghi nội dung các giải thưởng vào file prize.csv
	public static boolean writeToCsvPrize(String path) throws ClassNotFoundException, SQLException {
		File file = new File(path);
		FileWriter fw;
		try {
			fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);

			List<List<String>> table = getTable();

			if (Data.convertDate(table.get(0).get(1), 0) == Data.intDateCurrent()) {
				List<Prize> list = getAllPrize(table);
				bw.write("\ufeff" + "id_prize,name_prize,value_prize,created_date,updated_date");
				bw.newLine();
				for (Prize p : list) {
					p.writeData(file, fw, bw);
				}
				bw.close();
				fw.close();
			} else {
				bw.close();
				fw.close();
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}

package script1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Data {
	// Lấy ra bảng xổ số ở trên web (https://www.minhngoc.net.vn/xo-so-mien-nam.html)
	public static List<List<String>> getTable() throws IOException {
		Document d = Jsoup.connect("https://www.minhngoc.net.vn/xo-so-mien-nam.html").timeout(6000).get();
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

		Elements td2 = td.get(1).select("table.rightcl");

		for (Element element : td2) {
			Elements col = element.select("tr");
			ArrayList<String> strList = new ArrayList<>();
			for (Element e : col) {
				strList.add(e.text());
			}
			table.add(strList);
		}
		return table;
	}
    // Lấy ra ds tỉnh thành
	public static List<Province> getAllProvince(List<List<String>> table) {
		List<Province> re = new ArrayList<>();
		String current = table.get(0).get(1);
		for (int i = 1; i < table.size(); i++) {
			String name_province = table.get(i).get(0);
			String id_province = table.get(i).get(1);
			String[] split = current.split("/");
			re.add(new Province(id_province, name_province, split[2] + "-" + split[1] + "-" + split[0], "9999-12-31"));
		}
		return re;
	}
    // Lấy ra số lượng tỉnh
	public static int getAmoutProvince() throws IOException {
		Document d = Jsoup.connect("https://www.minhngoc.net.vn/xo-so-mien-nam.html").timeout(6000).get();
		Elements ele = d.select("div.box_kqxs");

		Elements content = ele.get(0).select("div.content table.bkqmiennam > tbody > tr");

		Elements td = content.get(0).select("> td");

		Elements td2 = td.get(1).select("table.rightcl");
		return td2.size();
	}
    // Ghi nội dung các tỉnh vào file province.csv
	public static boolean writeToCsvProvince(String path) {
		File file = new File(path);
		FileWriter fw;
		try {
			fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);

			List<List<String>> table = getTable();
//			if (convertDate(table.get(0).get(1), 0) == intDateCurrent()) {
				List<Province> list = getAllProvince(table);
				bw.write("\ufeff" + "id_province,name_province,created_date,updated_date");
				bw.newLine();
				for (Province p : list) {
					p.writeData(file, fw, bw);
				}
				bw.close();
				fw.close();
//			} else {
//				bw.close();
//				fw.close();
//				return false;
//			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
    // Lấy ra danh sách kết quả xổ số
	public static List<Lotto> getAllLotto(List<List<String>> table) {
		List<Lotto> re = new ArrayList<>();
		String current = table.get(0).get(1);
		for (int i = 1; i < table.size(); i++) {
			for (int j = 2; j < table.get(0).size(); j++) {
				String numbers = table.get(i).get(j);
				String[] split = numbers.split(" ");

				String idProvince = table.get(i).get(1);
				String idPrice = table.get(0).get(j);
				for (int k = 0; k < split.length; k++) {
					re.add(new Lotto(idProvince, idPrice, split[k], "ER", convertDate(current, 0), "9999-12-31"));
				}
			}
		}
		return re;
	}

	// Chuyển ngày(current) thành id trong bảng date_dim
	public static int convertDate(String current, int dateFormat) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		// set 2022-10-18 is 6500
		Date date1 = Date.valueOf("2022-10-18");
		Date date2;
		if (dateFormat == 0) {
			String[] split = current.split("/");
			date2 = Date.valueOf(split[2] + "-" + split[1] + "-" + split[0]);
		} else {
			date2 = Date.valueOf(current);
		}
		c1.setTime(date1);
		c2.setTime(date2);
		long noDay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
		int dis = (int) noDay;

		return 6500 + dis;
	}
    // Chuyển ngày hiện tại thành id trong bảng date_dim
	public static int intDateCurrent() {
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return convertDate(current.format(formatter), 1);
	}
	// Ghi nội dung các kết quả xổ số vào file lotto.csv
	public static boolean writeToCsvLotto(String path) {
		File file = new File(path);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			List<List<String>> table = getTable();

//			if (convertDate(table.get(0).get(1), 0) == intDateCurrent()) {
				List<Lotto> list = getAllLotto(table);
				bw.write("\ufeff" + "natural_key,id_province,id_prize,number,status,created_date,updated_date");
				bw.newLine();
				for (Lotto p : list) {
					p.writeData(file, fw, bw);
				}
				bw.close();
				fw.close();
//			} else {
//				bw.close();
//				fw.close();
//				return false;
//			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
//		System.out.println(writeToCsvLotte("script1Data/23-10-2022/lotto.csv"));
//		System.out.println(writeToCsvProvince("script1Data/23-10-2022/province.csv"));
		
	}


}

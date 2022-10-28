package script1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMain {
	private Dao dao = new Dao();
	private String path;

	public MyMain() {
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String folderName = current.format(formatter);
		File f = new File("script1Data");
		f.mkdir();
		path = "script1Data/" + folderName;
	}
    // chuyển String dạng yyyy-MM-dd sang kiểu dữ liệu Date
	public java.util.Date formatDate(String date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(date);
	}
    // thực hiện chạy script1
	public int run() throws ParseException, IOException, ClassNotFoundException, SQLException {
		if (dao.hasLog()) {
			return flowByStatus();
		} else {
			List<Config> list = dao.getConfig();
			if (list.size() == 0)
				return 1;
			LocalDateTime current = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dao.addLog(list.get(0).getId_config(), "null", dao.getIdContactor(script2.Config.USERNAME),
					new java.sql.Date(formatDate(current.format(formatter)).getTime()),
					new java.sql.Date(formatDate("9999-12-31").getTime()));
			dao.addLog(list.get(1).getId_config(), "null", dao.getIdContactor(script2.Config.USERNAME),
					new java.sql.Date(formatDate(current.format(formatter)).getTime()),
					new java.sql.Date(formatDate("9999-12-31").getTime()));
			return flowByStatus();
		}
	}
    // hỗ trợ chạy script1 từ kiểm tra status log tới hết
	public int flowByStatus() throws IOException, ClassNotFoundException, SQLException {
		if (dao.getStatusLog().equals("ERER")) {
			return 1;
		} else {
			File f = new File(path);
			f.mkdir();
			System.out.println(PrizeData.writeToCsvPrize(path + "/prize.csv"));
			System.out.println(Data.writeToCsvProvince(path + "/province.csv"));
			System.out.println(Data.writeToCsvLotto(path + "/lotto.csv"));
			System.out.println(loadDateDim("F:\\general folder\\monHocNam4Ki1\\dataWarehouse\\document\\Date_Dim\\date_dim_without_quarter.csv", path + "/date_dim_without_quarter.csv"));
			if (checkData()) {
				dao.setStatusLog("ER");
			} else {
				dao.setStatusLog("EN");
			}
			return 1;
		}
	}

	// đọc dữ liệu từ file.csv
	public List<List<String>> readCsv(String path) {
		List<List<String>> re = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				re.add(Arrays.asList(values));
			}
		} catch (IOException e) {
			System.out.println("loi");
		}
		return re;
	}

	// kiểm tra số dòng dữ liệu lấy về có đủ k
	public boolean checkRow() throws IOException {
		return (readCsv(path + "/lotto.csv").size() - 1) % (readCsv(path + "/province.csv").size() - 1) == 0
				&& (readCsv(path + "/prize.csv").size() - 1) == 9;
	}

	// kiểm tra từng ô dữ liệu có trống k
	public boolean checkCell(String path) {
		List<List<String>> temp = readCsv(path);
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < temp.get(i).size(); j++) {
				if (temp.get(i).get(j).equals(" ") || temp.get(i).get(j).equals("")) {
					return false;
				}
			}
		}
		return true;
	}

	// kiểm tra dữ liệu có đủ k
	public boolean checkData() throws IOException {
		return checkRow() && checkCell(path + "/lotto.csv") && checkCell(path + "/province.csv")
				&& checkCell(path + "/prize.csv");
	}
	// load date dim
	public boolean loadDateDim(String source, String dest) throws IOException {
		File file = new File(source);
		if (!file.exists()) return false;
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
		byte[] buffer = new byte[1024];
		int length;
        while ((length = bis.read(buffer)) > 0) {
            bos.write(buffer, 0, length);
        }
        bis.close();
        bos.close();
		return true;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException, SQLException {
		MyMain myMain = new MyMain();
		System.out.println(myMain.run());
	}

}

package script2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class Handle_files {
	
	public static String file_path_upload = "E:\\learnGo\\src\\scrapper\\";
	public static String file_path_download = "E:\\learnGo\\src\\scrapper\\";


	public static FTPClient ftpClient;

	/*
	 * Connect FTP-Server
	 */
	public static void connection() {
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(Config.SERVER);
			ftpClient.login(Config.USERNAME, Config.PASSWORD);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Disconnect FTP-Server
	 */
	public static void disconnect(FTPClient ftpClient) {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Upload file to FTP-Server
	 */
	public static boolean upload_file(String filename) {
		boolean status = false;
		try {
			connection();

			File firstLocalFile = new File(file_path_upload + filename + ".csv");

			String firstRemoteFile = "/data_extract/" + filename + ".csv";

			InputStream inputStream = new FileInputStream(firstLocalFile);

			status = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			disconnect(ftpClient);
		}
		return status;
	}

	/*
	 * Download file since FTP-Server
	 */
	public static boolean download_file(String filename) {
		boolean status = false;
		try {
			connection();

			String remoteFile1 = "/data_extract/" + filename + ".csv";

			File downloadFile1 = new File(file_path_download + filename + ".csv");

			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));

			status = ftpClient.retrieveFile(remoteFile1, outputStream1);

			outputStream1.close();
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			disconnect(ftpClient);
		}
		return status;
	}
	
	/*
	 * Delete file upload FTP Server
	 */
	public static boolean delete_file(String filename) {
		boolean status = false;
		try {
			connection();

			String fileToDelete = "/data_extract/" + filename + ".csv";

			boolean deleted = ftpClient.deleteFile(fileToDelete);

			status = deleted ? deleted : status;

		} catch (IOException ex) {
			System.out.println("Oh no, there was an error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			disconnect(ftpClient);
		}
		return status;
	}

	public static void main(String[] args) {
//		System.out.println(upload_file("10-15-2022-KA"));
//		System.out.println(upload_file("lotto_29_09_2022(19130154)"));
//		System.out.println(upload_file("lotto_28_09_2022(19130154)"));

//		System.out.println(download_file("lotto_30_09_2022(19130154)"));
//		System.out.println(download_file("lotto_29_09_2022(19130154)"));
//		System.out.println(download_file("lotto_28_09_2022(19130154)"));

	}
}

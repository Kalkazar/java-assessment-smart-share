package com.ftd.smartshare.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ftd.smartshare.dto.DownloadRequestDto;
import com.ftd.smartshare.dto.UploadRequestDto;
import com.ftd.smartshare.dto.ViewDto;
import com.ftd.smartshare.dto.ViewRequestDto;

public class SmartShareDao {
	public static final String URL = "jdbc:postgresql://localhost:5432/postgres/smartshare";
	public static final String USER = "postgres";
	public static final String PASSWORD = "bondstone";

	public SmartShareDao() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to load the PostgreSQL Driver");
		}
	}
	
	public byte[] downloadFile(DownloadRequestDto download) {
		String fileName = download.getFileName();
		String password = download.getPassword();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Statement stmnt = connection.createStatement();
			ResultSet rsltSet = stmnt.executeQuery("SELECT * from smartshare.files;");
			while (rsltSet.next()) {
				deleteOldFiles(rsltSet, connection);
				if (rsltSet.getString("file_name").equals(fileName) && rsltSet.getString("password").equals(password)) {
					String sql = "UPDATE smartshare.files SET total_downloads = total_downloads + 1 WHERE file_name = ?;";
					PreparedStatement prpstmnt = connection.prepareStatement(sql);
					prpstmnt.setString(1, rsltSet.getString("file_name"));
					prpstmnt.executeUpdate();
					System.out.println("BYTES: "+rsltSet.getBytes("file"));
					return rsltSet.getBytes("file");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteOldFiles(ResultSet rsltSet, Connection cnnctn) throws SQLException {// way to not type "0" twice?
		String sql;
		long timeLeft = rsltSet.getTimestamp("expiry_time").getTime() - rsltSet.getTimestamp("time_created").getTime();
		int downloadsLeft = rsltSet.getInt("max_downloads") - rsltSet.getInt("total_downloads");
		if(timeLeft <= 0 || downloadsLeft < 0) {
			sql = "DELETE FROM smartshare.files WHERE file_name = ?;";
			PreparedStatement prpstmnt = cnnctn.prepareStatement(sql);
			prpstmnt.setString(1, rsltSet.getString("file_name"));
			prpstmnt.executeUpdate();
		}
	}
	
	public boolean uploadFile(UploadRequestDto upload) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);) {
			if (!hasFileName(upload.getFileName(), connection)) {
				String sql = "INSERT INTO smartshare.files "
						+ "(file_name, file, time_created, expiry_time, max_downloads, total_downloads, password) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, upload.getFileName());
				statement.setBytes(2, upload.getFileBytes());
				statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				statement.setTimestamp(4, new Timestamp(System.currentTimeMillis() + upload.getExpiration() * 60000));
				statement.setInt(5, upload.getMaxDownloads());
				statement.setInt(6, 0);
				statement.setString(7, upload.getPassword());
				statement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasFileName(String fileName, Connection cnnctn) throws SQLException {
		Statement stmnt = cnnctn.createStatement();
		ResultSet resultSet = stmnt.executeQuery("SELECT * from smartshare.files;");
		while (resultSet.next()) {
			if (resultSet.getString("file_name") == fileName) {
				return true;
			}
		}
		return false;
	}
	
	public long[] viewFile(ViewRequestDto viewRequestDto) {
		String fileName = viewRequestDto.getFileName();
		String password = viewRequestDto.getPassword();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Statement stmnt = connection.createStatement();
			ResultSet rsltSet = stmnt.executeQuery("SELECT * from smartshare.files;");
			while (rsltSet.next()) {
				deleteOldFiles(rsltSet, connection);
				if (rsltSet.getString("file_name").equals(fileName) && rsltSet.getString("password").equals(password)) {
					long timeLeft = rsltSet.getTimestamp("expiry_time").getTime() - rsltSet.getTimestamp("time_created").getTime();
					long downloadsLeft = (long) rsltSet.getInt("max_downloads") - rsltSet.getInt("total_downloads");
					long[] returnArray = {downloadsLeft, timeLeft};
					return returnArray;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
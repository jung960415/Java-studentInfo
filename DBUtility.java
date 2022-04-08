package com.mire.sixclass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtility {
	
	public static Connection getConnection() {
		
		Connection con = null;
		FileReader fr = null;
		Properties properties = new Properties();
		
		try {
			fr = new FileReader("src\\com\\mire\\sixclass\\studentinfodb.properties");
			properties.load(fr);
			
			String DRIVER = properties.getProperty("DRIVER");
			String URL = properties.getProperty("URL");
			String userID = properties.getProperty("userID");
			String userPassword = properties.getProperty("userPassword");
			
			//드라이버 적재
			Class.forName(DRIVER);
			//데이터베이스 연결
			con = (Connection) DriverManager.getConnection(URL, userID, userPassword);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return con;
	}
}

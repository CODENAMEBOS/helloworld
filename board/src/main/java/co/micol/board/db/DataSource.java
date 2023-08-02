package co.micol.board.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
	// SingleTon
	private static DataSource dataSource = new DataSource();
	private String driver;
	private String url;
	private String user;
	private String password;
	private Connection connection;

	private DataSource() {
		getProperties();
		try {
			Class.forName(driver);
			System.out.println("DB 연결 성공!!!");
		} catch (ClassNotFoundException e) {
			System.out.println("DB 연결 실패!!!");
		}
	}

	public static DataSource getInstance() {
		return dataSource;
	}

	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private void getProperties() {
		String resource = "/db.properties";
		Properties properties = new Properties();

		InputStream inputStream = getClass().getResourceAsStream(resource);
		try {
			properties.load(inputStream);
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

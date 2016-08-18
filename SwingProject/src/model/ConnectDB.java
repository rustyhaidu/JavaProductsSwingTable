package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

	public static Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "");
		} catch (Exception e) {
			connection = null;
		}
		return connection;
	}

}

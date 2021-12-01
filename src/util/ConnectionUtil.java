package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:data/db/HandiverseDB.db");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static boolean closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			return false;
		}

		return false;
	}

}
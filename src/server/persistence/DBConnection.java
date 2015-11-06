package server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DBConnection {
	public static String DBNAME;
	public static String DBUSER;
	public static String DBPASS;
	public static String DBHOST;
	public static String DBPORT;
	private String url;

	public DBConnection() {
		url = "jdbc:mysql://" + DBHOST + ":" + DBPORT + "/" + DBNAME;
	}

	public boolean save(String insert) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			con = DriverManager.getConnection(url, DBUSER, DBPASS);
			st = con.prepareStatement(insert);
			result = st.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public int update(String update) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;

		try {
			con = DriverManager.getConnection(url, DBUSER, DBPASS);
			st = con.prepareStatement(update);
			result = st.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	

}

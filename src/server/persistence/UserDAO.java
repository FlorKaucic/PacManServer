package server.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.logic.User;
import server.config.Config;

public class UserDAO {

	public static void save(User user, String password) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("INSERT INTO tbl_user (username, password, nickname, won, lost) VALUES (?,?,?,?,?);");
		
		st.setString(1, user.getUsername());
		st.setString(2, password);
		st.setString(3, user.getUsername());
		st.setInt(4, user.getWonMatches());
		st.setInt(5, user.getLostMatches());
		
		st.execute();
		
		st.close();
		con.close();
	}

	public static User get(String username, String password) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("SELECT * FROM tbl_user WHERE username = ? and password = ?;");
		
		st.setString(1, username);
		st.setString(2, password);
		
		st.execute();
		
		st.close();
		con.close();
		
		return new User();
	}
	
	public static void update(User user) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("INSERT INTO tbl_user (username, password, nickname, won, lost) VALUES (?,?,?,?,?);");
		
		st.setString(1, user.getUsername());
		st.setString(3, user.getUsername());
		st.setInt(4, user.getWonMatches());
		st.setInt(5, user.getLostMatches());
		
		st.execute();
		
		st.close();
		con.close();
	}
	
}

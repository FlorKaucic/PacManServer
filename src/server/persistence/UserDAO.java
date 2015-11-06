package server.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.logic.User;
import game.logic.builder.UserBuilder;
import server.config.Config;

public class UserDAO {

	public static User save(String username, String password) throws SQLException {
		User user = UserBuilder(username).build();
				
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("INSERT INTO tbl_user (username, password, nickname, won, lost) "
				+ "VALUES (?,?,?,?,?);");
				
		st.setString(1, user.getUsername());
		st.setString(2, password);
		st.setString(3, user.getUsername());
		st.setInt(4, user.getWonMatches());
		st.setInt(5, user.getLostMatches());
		
		boolean result = st.execute();
		
		st.close();	
		
		if(!result){
			con.close();
			return null;
		}
			
		st = con.prepareStatement("SELECT id FROM tbl_user WHERE username = ?;");
		st.setString(1, user.getUsername());
		
		ResultSet rs = st.executeQuery();		
		
		if(!rs.first()){
			con.close();
			return null;
		}
		
		user.setId(rs.getInt("id"));
		
		rs.close();
		st.close();	
		con.close();
		
		return user;
	}

	public static User get(String username, String password) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("SELECT * FROM tbl_user WHERE username = ? and password = ?;");
		ResultSet rs;
		
		st.setString(1, username);
		st.setString(2, password);
		
		rs = st.executeQuery();
		
		st.close();
		con.close();
		
		User user = null;
		
		while(rs.first()){
			int id = rs.getInt("id");
			String nickname = rs.getString("nickname");
			int won = rs.getInt("won");
			int lost = rs.getInt("lost");
			
			user = new UserBuilder(id, username).withNickname(nickname).withWonMatches(won).withLostMatches(lost).build();
		}
		
		return user;
	}
	
	public static int update(User user) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("UPDATE tbl_usuario SET nickname = ?, won = ?, lost = ? WHERE id = ?");
		
		st.setString(1, user.getNickname());
		st.setInt(2, user.getWonMatches());
		st.setInt(3, user.getLostMatches());
		st.setInt(4, user.getId());
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}
	
}

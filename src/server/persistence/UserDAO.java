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
		User user = new UserBuilder(username).build();
				
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		
		PreparedStatement st = con.prepareStatement("SELECT * FROM tbl_user WHERE username = ?;");
				
		st.setString(1, user.getUsername());
		
		ResultSet rs = st.executeQuery();
		
		if(rs.next()){
			rs.close();
			st.close();	
			con.close();
			return null;
		}
		
		rs.close();		
		st.close();
		
		st = con.prepareStatement("INSERT INTO tbl_user (username, password, nickname, won, lost) "
				+ "VALUES (?,?,?,?,?);");
		
		st.setString(1, user.getUsername());
		st.setString(2, password);
		st.setString(3, user.getUsername());
		st.setInt(4, user.getWonMatches());
		st.setInt(5, user.getLostMatches());
		
		st.execute();
		
		st.close();	
							
		st = con.prepareStatement("SELECT id FROM tbl_user WHERE username = ?;");
		st.setString(1, user.getUsername());
		
		rs = st.executeQuery();		
		
		if(!rs.first()){
			rs.close();
			st.close();	
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
		PreparedStatement st = con.prepareStatement("SELECT * FROM tbl_user WHERE username = ? AND password = ? AND enabled = TRUE;");
		ResultSet rs;
		
		st.setString(1, username);
		st.setString(2, password);
		
		rs = st.executeQuery();
				
		User user = null;
		
		if(rs.first()){
			int id = rs.getInt("id");
			String nickname = rs.getString("nickname");
			int won = rs.getInt("won");
			int lost = rs.getInt("lost");
			
			user = new UserBuilder(id, username).withNickname(nickname).withWonMatches(won).withLostMatches(lost).build();
		}

		rs.close();
		st.close();
		con.close();
		
		return user;
	}
	
	public static int update(User user) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("UPDATE tbl_user SET nickname = ?, won = ?, lost = ? WHERE id = ?");
		
		st.setString(1, user.getNickname());
		st.setInt(2, user.getWonMatches());
		st.setInt(3, user.getLostMatches());
		st.setInt(4, user.getId());
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}
	
	public static void disable(int[] idlist) throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		
		String statement = new String("UPDATE tbl_user SET enabled = FALSE WHERE id IN (");
		
		for(int i = 0; i < idlist.length; i++)
			statement += "?, ";
		
		statement = statement.substring(0, statement.length()-2) + ")";
		
		System.out.println(statement);
		
		PreparedStatement st = con.prepareStatement(statement);
		
		for(int i = 0; i < idlist.length; i++)
			st.setInt(i+1, idlist[i]);
		
		st.executeUpdate();
		
		st.close();
		con.close();
	}
	
	public static User[] getAll() throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("SELECT id, username, enabled FROM tbl_user;");
		ResultSet rs = st.executeQuery();
		
		int cant = 0;
		while(rs.next())
			cant++;
		rs.beforeFirst();
		
		User[] users = new User[cant];
		
		for(int i = 0; i < cant; i++){
			rs.next();
			
			int id = rs.getInt("id");
			String username = rs.getString("username");
			boolean enabled = rs.getBoolean("enabled");
			
			users[i] = new UserBuilder(id, username).withEnabled(enabled).build();
		}
		
		rs.close();
		st.close();
		con.close();
		
		return users;
	}
	
	public static User[] getAllEnabled() throws SQLException {
		String url = "jdbc:mysql://" + Config.get("dbhost") + ":" + Config.get("dbport")+ "/" + Config.get("dbname");
		Connection con = DriverManager.getConnection(url, Config.get("dbuser"), Config.get("dbpass"));
		PreparedStatement st = con.prepareStatement("SELECT username, won, lost  FROM tbl_user WHERE enabled = true;");
		ResultSet rs = st.executeQuery();
		
		int cant = 0;
		while(rs.next())
			cant++;
		rs.beforeFirst();
		
		User[] users = new User[cant];
		
		for(int i = 0; i < cant; i++){
			rs.next();
			
			String username = rs.getString("username");
			int won = rs.getInt("won");
			int lost = rs.getInt("lost");
			
			users[i] = new UserBuilder(username).withWonMatches(won).withLostMatches(lost).build();
		}
		
		rs.close();
		st.close();
		con.close();
		
		return users;
	}
	
}

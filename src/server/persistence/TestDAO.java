package server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class TestDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String url = "jdbc:mysql://localhost:3306/db_pacman";

		try {
			con = DriverManager.getConnection(url, "root", "");
			st = con.createStatement();
			PreparedStatement statement;
			//st.execute("INSERT INTO tbl_usuario(username,password) VALUES('test','1234');");

			
			
			rs = st.executeQuery("SELECT * FROM tbl_usuario");
			while (rs.next()){
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String nickname = rs.getString(4);
				int won = rs.getInt(5);
				int lost = rs.getInt(6);
				System.out.println(id + 
						" " + username + 
						" " + password + 
						" " + nickname +
						" " + won + 
						" " + lost);
//				statement = con.prepareStatement("UPDATE tbl_usuario SET nickname = ? WHERE username = ?;");
//				statement.setString(1, username);
//				statement.setString(2, username);
//				statement.executeUpdate();
			}
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
	}
}

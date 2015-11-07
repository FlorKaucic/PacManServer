package server.conn;

import java.sql.SQLException;

import game.logic.User;
import server.persistence.UserDAO;

public class ServerProtocol {
	// Registrar usuario:
	// recibe
	// LOGUP username password
	// devuelve
	// LOGUPOK PACMAN/GHOST id nickname
	// LOGUPFAILED [...]
	
	// Ingresar usuario:
	// recibe
	// LOGIN username password
	// devuelve
	// LOGINOK PACMAN/GHOST id nickname
	// LOGINFAILED [...]
	
	
	
	public static String processInput(String input){
		if(input.startsWith("LOGUP")){
			String[] data = input.substring(5).split(" ");
			User user = null;
			try {
				user = UserDAO.save(data[0], data[1]);
			} catch (SQLException e) {
				//CHANGE
				e.printStackTrace();
			}
			if(user==null)
				return "LOGUPFAILED";
			return "LOGUPOK "+user.getId()+" "+user.getNickname();
		}
		if(input.startsWith("LOGIN")){
			String[] data = input.substring(5).split(" ");
			User user = null;
			try {
				user = UserDAO.get(data[0], data[1]);
			} catch (SQLException e) {
				// CHANGE
				e.printStackTrace();
			}
			if(user==null)
				return "LOGINFAILED";
			return "LOGINOK "+user.getId()+" "+user.getNickname();
		}
//		if(input.startsWith("GETMAP")){
//			
//		}
		return null;
	}	
}

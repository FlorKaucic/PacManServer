package server.conn;

import java.sql.SQLException;

import game.logic.Match;
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
			String[] data = input.substring(6).split(" ");
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
			String[] data = input.substring(6).split(" ");
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
		if(input.startsWith("GETMAP")){
			// CHANGE
			// AGREGAR A LISTA DE BROADCAST
			return "MAPOK " + Match.getInstance().getMapAsStrings();			
		}
		if(input.startsWith("GETALLSTATS")){
			try {
				User[] users = UserDAO.getAll();

				StringBuffer str = new StringBuffer();
				str.append("STATSOK ");
				for(User u : users)
					str.append(u.getUsername()+" "+u.getWonMatches()+" "+u.getLostMatches()+" ");
				return str.toString();
			} catch (SQLException e) {
				return "STATSFAILED";
			}
		}
		return null;
	}	
}

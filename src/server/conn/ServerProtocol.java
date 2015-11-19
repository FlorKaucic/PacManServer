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

	public static String processInput(String input) {
		if (input.startsWith("LOGUP")) {
			return processLogup(input.substring(6));
		}
		if (input.startsWith("LOGIN")) {
			return processLogin(input.substring(6));
		}
		if (input.startsWith("GETMAP")) {
			// CHANGE
			// AGREGAR A LISTA DE BROADCAST
			return "MAPOK " + Match.getInstance().getMapAsString();
		}
<<<<<<< HEAD
		if(input.startsWith("GETALLSTATS")){
			try {
				User[] users = UserDAO.getAllEnabled();

				StringBuffer str = new StringBuffer();
				str.append("STATSOK ");
				for(User u : users)
						str.append(u.getUsername()+" "+u.getWonMatches()+" "+u.getLostMatches()+" ");
				return str.toString();
			} catch (SQLException e) {
				return "STATSFAILED";
			}
=======
		if (input.startsWith("GETALLSTATS")) {
			return processStats();
>>>>>>> branch 'master' of https://github.com/FlorKaucic/PacManServer.git
		}
		return null;
	}

	private static String processStats() {
		try {
			User[] users = UserDAO.getAllEnabled();
			StringBuffer str = new StringBuffer();
			str.append("STATSOK ");
			for (User u : users)
				str.append(u.getUsername() + " " + u.getWonMatches() + " " + u.getLostMatches() + " ");
			return str.toString();
		} catch (SQLException e) {
			return "STATSFAILED";
		}
	}

	private static String processLogin(String input) {
		boolean viewer = false;
		if (input.startsWith("VIEWER")) {
			input = input.substring(7);
			viewer = true;
		}
		String[] data = input.split(" ");
		User user = null;
		try {
			user = UserDAO.get(data[0], data[1]);
		} catch (SQLException e) {
			// CHANGE
			e.printStackTrace();
		}
		if (user == null)
			return "LOGINFAILED";
		String profile = "VIEWER";
		if (!viewer) {
			int pos = Match.getInstance().addCharacter();
			if (pos == 0)
				profile = "PACMAN";
			if (pos > 0)
				profile = "GHOST" + pos;
		}
		return "LOGINOK " + profile + " " + user.getId() + " " + user.getNickname();
	}

	private static String processLogup(String input) {
		String[] data = input.split(" ");
		User user = null;
		try {
			user = UserDAO.save(data[0], data[1]);
		} catch (SQLException e) {
			//CHANGE
			e.printStackTrace();
		}
		if (user == null)
			return "LOGUPFAILED";
		return "LOGUPOK " + user.getId() + " " + user.getNickname();
	}
}

package server.conn;

import java.awt.Color;
import java.sql.SQLException;

import game.logic.Match;
import game.logic.User;
import server.conn.alert.ClientAlert;
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

	public static String processInput(ServerThread caller, String input) {
		if (input.startsWith("LOGUP")) {
			return processLogup(input.substring(6));
		}
		if (input.startsWith("LOGIN")) {
			return processLogin(caller, input.substring(6));
		}
		if (input.startsWith("GETMAP")) {
			return "MAPOK " + Match.getInstance().getMapAsString();
		}
		if (input.startsWith("GETALLSTATS")) {
			return processStats();
		}
		if (input.startsWith("READY")){
			return "READYOK";
		}
		if (input.startsWith("MOVE")){
			processMovement(caller, input.substring(5));
		}
		if (input.startsWith("PING"))
			return "PONG";
		return null;
	}

	private static void processMovement(ServerThread caller, String input) {
		Match.getInstance().setMovement(caller.getProfile(), Integer.parseInt(input));
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

	private static String processLogin(ServerThread caller, String input) {
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
			ClientAlert dialog = new ClientAlert("Error de inicio.");
			dialog.setBorderColor(Color.RED);
			dialog.setVisible(true);
		}
		if (user == null)
			return "LOGINFAILED";
		int profile = ((viewer)?-1:Match.getInstance().addCharacter());
		caller.setUser(user);
		caller.setProfile(profile);
		Match.getInstance().addListener(caller);
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

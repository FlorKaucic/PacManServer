package game.logic.builder;

import game.logic.User;

public class UserBuilder {
	private int id;
	private String username;
//	private char[] password;
	private String nickname;
	private int won = 0;
	private int lost = 0;

<<<<<<< HEAD
=======
//	public UserBuilder(int id, String username, char[] pass) {
//		this.id = id;
//		this.username = username;
//		password = pass;
//	}
	
>>>>>>> branch 'master' of https://github.com/FlorKaucic/PacManServer.git
	public UserBuilder(int id, String username) {
		this.id = id;
		this.username = username;
<<<<<<< HEAD
		//password = pass;
	}
	
	public UserBuilder(String username) {
		this.username = username;
=======
>>>>>>> branch 'master' of https://github.com/FlorKaucic/PacManServer.git
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
<<<<<<< HEAD
	}

	public char[] getPassword() {
		return password;
=======
>>>>>>> branch 'master' of https://github.com/FlorKaucic/PacManServer.git
	}

	public String getNickname() {
		return nickname;
	}

	public int getWon() {
		return won;
	}

	public int getLost() {
		return lost;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserBuilder withId(int id) {
		this.id = id;
		return this;
	}
	
	public UserBuilder withNickname(String nick) {
		nickname = nick;
		return this;
	}

	public UserBuilder withWonMatches(int w) {
		won = w;
		return this;
	}

	public UserBuilder withLostMatches(int l) {
		lost = l;
		return this;
	}

	public User build() {
		return new User(this);
	}
	
	
//	public char[] getPassword() {
//		return String.valueOf(password);
//	}
}

package game.logic.builder;

import game.logic.User;

public class UserBuilder {
	private int id;
	private String username;
	private char[] password;
	private String nickname;
	private int won = 0;
	private int lost = 0;

	public UserBuilder(int id, String username, char[] pass) {
		this.id = id;
		this.username = username;
		password = pass;
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}



	public char[] getPassword() {
		return password;
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
}

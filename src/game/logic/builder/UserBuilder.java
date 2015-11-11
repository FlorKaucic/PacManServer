package game.logic.builder;

import game.logic.User;

public class UserBuilder {
	private int id;
	private String username;
//	private char[] password;
	private String nickname;
	private int won = 0;
	private int lost = 0;
	private boolean enabled = true;
	
	
	public UserBuilder(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public UserBuilder(String username) {
		this.username = username;
	}
	
	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getNickname() {
		return this.nickname;
	}

	public int getWon() {
		return this.won;
	}

	public int getLost() {
		return this.lost;
	}
	
	public boolean getEnabled() {
		return this.enabled;
	}

	public UserBuilder withId(int id) {
		this.id = id;
		return this;
	}
	
	public UserBuilder withNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	public UserBuilder withWonMatches(int won) {
		this.won = won;
		return this;
	}

	public UserBuilder withLostMatches(int lost) {
		this.lost = lost;
		return this;
	}
	
	public UserBuilder withEnabled(boolean enabled){
		this.enabled = enabled;
		return this;
	}

	public User build() {
		return new User(this);
	}
	
	
//	public UserBuilder(int id, String username, char[] pass) {
//	this.id = id;
//	this.username = username;
//	password = pass;
//}
	
//	public char[] getPassword() {
//		return String.valueOf(password);
//	}
	
	
}

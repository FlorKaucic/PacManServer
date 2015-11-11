package game.logic;

//import java.util.Arrays;

import game.logic.builder.*;

public class User {
	private int id;
	private String username;
	// private char[] password;
	private String nickname;
	private int won;
	private int lost;
	private boolean enabled;

	public User(UserBuilder builder) {
		this.id = builder.getId();
		this.username = builder.getUsername();
		//this.password = builder.getPassword();
		this.nickname = builder.getNickname();
		this.won = builder.getWon();
		this.lost = builder.getLost();
		this.enabled = builder.getEnabled();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getWonMatches() {
		return won;
	}

	public void setWonMatch() {
		this.won++;
	}

	public int getLostMatches() {
		return lost;
	}

	public void setLostMatch() {
		this.lost++;
	}

	public boolean isEnabled() {
		return enabled;
	}

	//	public void setEnabled() {
	//		this.enabled = !this.enabled;
	//	}

	//	public boolean isPasswordCorrect(char[] password) {
	//	boolean correct = this.password.equals(password);
	//	cleanPassword();
	//	return correct;
	//}
	//
	//public void cleanPassword() {
	//	Arrays.fill(this.password,'0');
	//}
	//
	//public String getPassword(){
	//	return String.valueOf(this.password);
	//}
}

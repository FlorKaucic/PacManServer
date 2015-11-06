package game.logic;

import java.util.Arrays;

public class User {
	private int id;
	private String username;
	private char[] password;
	private String nickname;
	private int won;
	private int lost;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isPasswordCorrect(char[] password) {
		boolean correct = this.password.equals(password);
		cleanPassword();
		return correct;
	}
	
	public void cleanPassword() {
		Arrays.fill(this.password,'0');
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getWon() {
		return won;
	}
	
	public void setWon() {
		this.won++;
	}
	
	public int getLost() {
		return lost;
	}
	
	public void setLost() {
		this.lost++;
	}	
}



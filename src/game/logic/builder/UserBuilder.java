package game.logic.builder;
import game.logic.User;

public class UserBuilder {
	private int id;
	private String username;
	private char[] password;
	private String nickname;
	private int won=0;
	private int lost=0;
	
	public UserBuilder(int id, String username, char[] pass){
		this.id = id;
		this.username = username;
		password = pass;
	}
	
	public UserBuilder withNickname(String nick){
		nickname = nick;
		return this;
	}
	
	public UserBuilder withWonMatches(int w){
		won = w;
		return this;
	}
	
	public UserBuilder withLostMatches(int w){
		won = w;
		return this;
	}
	
	public User build(){
		return new User(this);
	}
	
	
	
}

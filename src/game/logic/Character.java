package game.logic;

<<<<<<< HEAD
public class Character {
	protected int desX;
	protected int desY;
	protected int vel;
	protected boolean poder;
	protected int vidaPoder;
=======
@SuppressWarnings("serial")
public class Character extends Drawable {
	int desX;
	int desY;
	int vel;
	boolean poder;
	int vidaPoder;
>>>>>>> branch 'master' of https://github.com/FlorKaucic/PacManServer.git
	
	public Character(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
	}
	
	public void move(){
		
	}
	public void checkPos(){
		
	}
	public void respawn(){
		
	}
}

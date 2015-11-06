package game.logic;

<<<<<<< HEAD
public class Ghost {
	private int killedPacman;
	private int killedFantasma;
=======
@SuppressWarnings("serial")
public class Ghost extends Character {
	int pacmansKilled;
	int ghostsKilled;
>>>>>>> branch 'master' of https://github.com/FlorKaucic/PacManServer.git
	
	public Ghost(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.pacmansKilled = 0;
		this.ghostsKilled = 0;
	}
	
	public void killedPacman(){
		this.pacmansKilled++;
	}
	
	public void killedGhost(){
		this.ghostsKilled++;
	}
	
	public int getPacmansKilled(){
		return this.pacmansKilled;
	}
	
	public int getGhostsKilled(){
		return this.ghostsKilled;
	}
	
}

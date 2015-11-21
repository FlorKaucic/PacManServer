package game.logic;

public class Pacman extends Character {
	
	int bolitasComidas=0;
	public Pacman(int posX, int posY, int width, int height, int vel, int lifeSpan, int powerSpan) {
		super(posX, posY, width, height, vel, lifeSpan, powerSpan);
	}
	
	
	public void morir(){
		
	}


	public void killedGhost() {
		// TODO Auto-generated method stub
		
	}
	
	public void comeBolita(){
		bolitasComidas++;
	}
	
}

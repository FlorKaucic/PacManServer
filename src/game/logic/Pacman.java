package game.logic;

import java.awt.Point;

public class Pacman extends Character {
	
	int bolitasComidas=0;
	public Pacman(int posX, int posY, int width, int height, int vel, int lifeSpan, int powerSpan) {
		super(posX, posY, width, height, vel, lifeSpan, powerSpan);
	}
	
	
	@Override
	public void respawn(){
		Point r = Match.getInstance().getRespawnPoint();
		this.posX = (int)r.getX();
		this.posY = (int)r.getY();
		this.desX = 0;
		this.desY = 0;
		this.life = 0;
		this.moving = false;
	}


	public void killedGhost() {
		
	}
	
	public void comeBolita(){
		bolitasComidas++;
	}
	
}

package game.logic;

import server.config.Config;

public class Ghost extends Character {
	private int pacmansKilled;
	private int ghostsKilled;
	
	public Ghost(int posX, int posY, int width, int height, int vel, int lifeSpan, int powerSpan) {
		super(posX, posY, width, height, vel, lifeSpan, powerSpan);
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
	
	public void respawn(){
		this.posX = Integer.parseInt(Config.get("ghost_posX"));
		this.posY = Integer.parseInt(Config.get("ghost_posX"));
		this.desX = 0;
		this.desY = 0;
		this.life = 0;
		this.moving = false;
	}

	public void freeze() {
		// TODO Auto-generated method stub
		
	}
	
}

package game.logic;

public class Character extends Drawable {
	protected int desX;
	protected int desY;
	protected int vel;
	protected int power;
	protected int powerSpan;
	protected int life;
	protected int lifeSpan;
	protected boolean moving;
	
	public Character(int posX, int posY, int width, int height, int vel, int lifeSpan, int powerSpan) {
		super(posX, posY, width, height);
		this.vel = vel;
		this.moving = false;
		this.life = 1;
		this.power = -1;
		this.lifeSpan = lifeSpan;
		this.powerSpan = powerSpan;
	}
	
	public void update(){
		this.posX += this.vel * this.desX;
		this.posY += this.vel * this.desY;
	}
	
	public void checkPos(){
		
	}
	
	public void respawn(){
		this.posX = 50;
		this.posY = 50;
	}

	public int getDesX() {
		return desX;
	}

	public void setDesX(int desX) {
		this.desX = desX;
	}

	public int getDesY() {
		return desY;
	}

	public void setDesY(int desY) {
		this.desY = desY;
	}

	public int getPower() {
		return power;
	}

	public void setPower() {
		this.power++;
	}

	public int getLife() {
		return life;
	}

	public void setLife() {
		this.life++;
	}

	public int getVel() {
		return vel;
	}

	public boolean isMoving() {
		return moving;
	}

	public int getPowerSpan() {
		return powerSpan;
	}

	public int getLifeSpan() {
		return lifeSpan;
	}


	
}

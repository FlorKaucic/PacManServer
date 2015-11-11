package game.logic;

@SuppressWarnings("serial")
public class Character extends Drawable {
	protected int desX;
	protected int desY;
	protected int vel;
	protected int power = -1;
	protected int powerSpan;
	protected int life;
	protected int lifeSpan;
	
	public Character(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
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

	public void setDesX(int desX) {
		this.desX = desX;
	}

	public void setDesY(int desY) {
		this.desY = desY;
	}
	
	
}

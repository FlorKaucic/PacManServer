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

	public void setDesX(int desX) {
		this.desX = desX;
	}

	public void setDesY(int desY) {
		this.desY = desY;
	}

	/*
	collision = False
	        other_pos = other_object.get_position()
	        distance = dist(self.pos, other_pos)
	        radius = self.radius + other_object.get_radius()
	        if (distance - radius) <= 0:
	            collision = True
	        return collision
*/
	
	public int getPower(){
		return power;
	}
	
}

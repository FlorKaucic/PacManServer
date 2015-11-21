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
	protected int dir;
	
	public Character(int posX, int posY, int width, int height, int vel, int lifeSpan, int powerSpan) {
		super(posX, posY, width, height);
		this.vel = vel;
		this.moving = false;
		this.life = 1;
		this.power = -1;
		this.lifeSpan = lifeSpan;
		this.powerSpan = powerSpan;
		this.dir = -1;
	}
	
	public boolean update(){
		int path = Match.getInstance().getPath(this.posX, this.posY);
		boolean ret = false;
		if(this.checkPos(path))
			ret = true;
		if(dir!=-1){
			if(dir==0&&canGoLeft(path)){
				this.desX=-1;
				this.desY=0;
				ret = true;
			}else if(dir==1&&canGoUp(path)){
				this.desX=0;
				this.desY=-1;
				ret = true;
			}else if(dir==2&&canGoRigth(path)){
				this.desX=1;
				this.desY=0;
				ret = true;
			}else if(dir==3&&canGoDown(path)){
				this.desX=0;
				this.desY=1;
				ret = true;
			}
		}
		this.posX += this.vel * this.desX;
		this.posY += this.vel * this.desY;
		return ret;
	}
	
	public boolean checkPos(int path){
		if (this.posX + this.width < 0){
			this.posX = 500;
			return true;
		}
		if (this.posX > 500){
			this.posX = -this.width;
			return true;
		}
		
		int x, y;
		x = this.posX % 50; // posicion x dentro del cuadrado
		y = this.posY % 50; // posicion y dentro del cuadrado
		
		if (desX < 0 && x < 5 && !canGoLeft(path)) {
			// Si va a la izq y esta a la altura de una posible pared	
			this.desX = 0;
			this.posX = this.posX - x + 5;
			this.life = 0;
			return true;
		}
		if (desX > 0 && x + this.width > 45 && !canGoRigth(path)) {
			// Si va a la izq y esta a la altura de una posible pared	
			this.desX = 0;
			this.posX = this.posX - (x + this.width) + 45;
			this.life = 0;
			return true;
		}
		if (desY < 0 && y < 5 && !canGoUp(path)) {
			// Si va a la izq y esta a la altura de una posible pared	
			this.desY = 0;
			this.posY = this.posY - y + 5;
			this.life = 0;
			return true;
		}
		if (desY > 0 && y + this.width > 45 && !canGoDown(path)) {
			// Si va a la izq y esta a la altura de una posible pared	
			this.desY = 0;
			this.posY = this.posY - (y + this.height) + 45;
			this.life = 0;
			return true;
		}
		
		if (desY != 0 && x != (this.width / 2)) {
			this.posX = (this.posX / 50) * 50 + (50 - this.width) / 2;
			return true;
		}
		if (desX != 0 && y != (this.height / 2)) {
			this.posY = (this.posY / 50) * 50 + (50 - this.height) / 2;
			return true;
		}
		return false;
//		this.desX = 0;
//		this.desY = 0;
	}
	
	private boolean canGoLeft(int path){
		return !(path == 2 || path == 3 || path == 5 || path == 8);
	}
	
	private boolean canGoRigth(int path){
		return !(path == 2 || path == 4 || path == 6 || path == 7);
	}
	
	private boolean canGoUp(int path){
		return !(path == 1 || path == 3 || path == 4 || path == 10);
	}
	
	private boolean canGoDown(int path){
		return !(path == 1 || path == 5 || path == 6 || path == 9);
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


	public void power() {
		// TODO Auto-generated method stub
	}
	public void setDir(int dir) {
		this.dir = dir;		
	}


	
}

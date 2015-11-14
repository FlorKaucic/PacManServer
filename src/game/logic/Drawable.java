package game.logic;

public class Drawable {
	protected String img;
	protected int posX;
	protected int posY;
	protected Integer imgX;
	protected Integer imgY;
	protected int width;
	protected int height;

	public Drawable(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.img = null;
		this.imgX = null;
		this.imgY = null;
	}
	
	public Drawable(int posX, int posY, int width, int height, String img) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.imgX = 0;
		this.imgY = 0;
		this.img = img;
	}

	public Drawable(int posX, int posY, int width, int height, String img, int value) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.imgX = value * width;
		this.imgY = 0;
		this.img = img;
	}

}

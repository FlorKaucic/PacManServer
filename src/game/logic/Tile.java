package game.logic;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class Tile extends Dibujable {
	private int value = 0;

	public Tile(int posX, int posY, int value, Image img) {
		this.posX = posX;
		this.posY = posY;
		this.value = value;
		this.img = img;
	}
	
	public void paint(Graphics g) {
		g.drawImage(this.img, 
				this.posX, this.posY, this.posX+this.width, this.posY+this.height,
				this.value * this.width, 0, (this.value + 1) * this.width, this.height, 
				null);
	}

}

package game.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

@SuppressWarnings("serial")
public class Dibujable extends Component {
	protected Image img;
	protected int posX;
	protected int posY;
	protected Integer imgX;
	protected Integer imgY;
	protected int width;
	protected int height;

	public Dibujable(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.img = null;
		this.imgX = null;
		this.imgY = null;
	}

	public Dibujable(int posX, int posY, int width, int height, Image img, int value) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.imgX = value * width;
		this.imgY = 0;
		this.img = img;
	}
	
	public void paint(Graphics g) {
		if(this.img != null){			
			g.drawImage(this.img, 
				this.posX, this.posY, this.posX + this.width, this.posY + this.height,
				this.imgX, this.imgY, this.imgX + this.width, this.imgY + this.height,
				null);
			return;
		}
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.ORANGE);
		g.fillOval(posX, posY, width, height);
	}
}

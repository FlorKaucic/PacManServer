package game.logic;

import java.awt.Point;
import java.util.ArrayList;
import game.map.MapReader;
import server.config.Config;
import server.conn.ServerThread;

public class Match {
	private static final int eatBall=10;
	private static final int eatSBall=50;
	private static final int pKillGhost=150;
	private static final int gKillGhost=200;
	private static final int killPacman=250;
	private int time = 0;
	private int[][] map;
	private ArrayList<Character> characters;
	private ArrayList<Drawable> balls = new ArrayList<Drawable>();
	private ArrayList<Drawable> superballs = new ArrayList<Drawable>();
	private int totalballs = 0;
	private static Match INSTANCE = null;
	private boolean playing = false;
	private ArrayList<ServerThread> listeners = null;
	private int []scores = new int [4];
	
	private Match() {
		characters = new ArrayList<Character>();
		balls = new ArrayList<Drawable>();
		superballs = new ArrayList<Drawable>();
		listeners = new ArrayList<ServerThread>();
		map = MapReader.read("res/map/map_0.in");
		playing = false;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int bolita = Math.floorDiv(Math.floorMod(map[i][j], 8), 2);
				if (bolita == 1)
					this.balls.add(new Drawable(j * 50 + 19, i * 50 + 19, 8, 8));
				if (bolita == 2)
					this.superballs.add(new Drawable(j * 50 + 15, i * 50 + 15, 15, 15));
			}
		}
		this.totalballs = this.balls.size();
	}

	public static Match getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Match();
		return INSTANCE;
	}

	public int addCharacter() {
		int x, y, w, h, v, l, p;
		if (characters.isEmpty()) {
			System.out.println("Pacman");
			x = Integer.parseInt(Config.get("pacman_posX"));
			y = Integer.parseInt(Config.get("pacman_posY"));
			w = Integer.parseInt(Config.get("pacman_width"));
			h = Integer.parseInt(Config.get("pacman_height"));
			v = Integer.parseInt(Config.get("pacman_vel"));
			l = Integer.parseInt(Config.get("pacman_lifespan"));
			p = Integer.parseInt(Config.get("pacman_powerspan"));
			System.out.println(x + " " + y + " " + w + " " + h + " " + v + " " + l + " " + p);
			characters.add(new Pacman(x, y, w, h, v, l, p));

			return 0;
		}
		if (characters.size() < 4) {
			System.out.println("Ghost");
			x = Integer.parseInt(Config.get("ghost_posX")) + 40 * characters.size() - 1;
			y = Integer.parseInt(Config.get("ghost_posY"));
			w = Integer.parseInt(Config.get("ghost_width"));
			h = Integer.parseInt(Config.get("ghost_height"));
			v = Integer.parseInt(Config.get("ghost_vel"));
			l = Integer.parseInt(Config.get("ghost_lifespan"));
			p = Integer.parseInt(Config.get("ghost_powerspan"));

			System.out.println(x + " " + y + " " + w + " " + h + " " + v + " " + l + " " + p);
			characters.add(new Ghost(x, y, w, h, v, l, p));

			return characters.size() - 1;
		}
		return -1;
	}

	public void addListener(ServerThread client) {
		this.listeners.add(client);
	}

	public void start() {
		StringBuffer msg = new StringBuffer("START ");
		for (int i = 0; i < characters.size(); i++) {
			Character c = characters.get(i);
			msg.append(c.getPosX() + " " + c.getPosY() + " " + c.getWidth() + " " + c.getHeight() + " " + c.getVel()
					+ " " + c.getLifeSpan() + " " + c.getPowerSpan() + "ELN");
		}
		String finalstring = msg.toString();
		broadcast(finalstring.substring(0, finalstring.length() - 3));
		time = 0;
		playing = true;
		//scores = new int [4];
	}

	public void finish() {
		INSTANCE = null;
		playing = false;
		listeners = null;
	}

	public boolean isFinished() {
		if (time >= 120){
			playing = false;
			
		}
		return !playing;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setTime() {
		this.time++;
	}

	public long getTime() {
		return time;
	}

	public int getNumberOfCharacters() {
		return this.characters.size();
	}

	public void collisions(Character car, int c) {

		int difX, difY, difTot, radTot;

		if (car == null)
			return;
		
		for (int i = 0; i < characters.size(); i++) {
			if(car != characters.get(i)){
				difX = (car.getPosX()+car.getHeight()/2) - (characters.get(i).getPosX() + characters.get(i).getHeight()/2);
				difY = (car.getPosY()+car.getWidth()/2) - (characters.get(i).getPosY()+characters.get(i).getWidth()/2);
	
				difTot = difX*difX + difY*difY;
				radTot = car.getHeight() / 2 + characters.get(i).getHeight() / 2;
				if (difTot - (radTot*radTot) <= 0) {
					
					if(car.getClass() == Pacman.class){
						
						if(car.getPower()>0){
							addScore(c, pKillGhost);
							broadcast("KILLGHOST " + i);
						}
						else{
							car.respawn();
							broadcast("MOVE " + c + " " + car.getPosX() + " " + car.getPosY() + " " + car.getDesX() + " " + car.getDesY());
							addScore(i, killPacman);
							broadcast("KILLPACMAN");
						}
					}
				
				
			}
			}
		}
	}



	public void collisionsBalls(Character car, int c) {
		int difX, difY, difTot, radTot;

		if (car == null)
			return;

		if (car.getClass() == Pacman.class) {
			//Pacman p = (Pacman) car;
			for (int i = 0; i < balls.size(); i++) {
				if(balls.get(i)!=null){
					difX = (car.getPosX()+car.getHeight()/2) - (balls.get(i).getPosX() + balls.get(i).getHeight()/2);
					difY = (car.getPosY()+car.getWidth()/2) - (balls.get(i).getPosY()+balls.get(i).getWidth()/2);
		
					difTot = difX*difX + difY*difY;
					radTot = car.getHeight() / 2 + balls.get(i).getHeight() / 2;
				if (difTot - (radTot*radTot) <= -car.getWidth()/4) {
					broadcast("BALLDOWN " + i);
					addScore(c, eatBall);
					balls.set(i, null);
					
				}
				}
			}
		}
		
		for (int i = 0; i < superballs.size(); i++) {
			if(superballs.get(i)!=null){
				difX = (car.getPosX()+car.getHeight()/2) - (superballs.get(i).getPosX() + superballs.get(i).getHeight()/2);
				difY = (car.getPosY()+car.getWidth()/2) - (superballs.get(i).getPosY()+superballs.get(i).getWidth()/2);
	
				difTot = difX*difX + difY*difY;
				radTot = car.getHeight() / 2 + superballs.get(i).getHeight() / 2;
			if (difTot - (radTot*radTot) <= -car.getWidth()/4) {
				broadcast("SBALLDOWN " + i);
				addScore(c, eatSBall);
				superballs.set(i, null);
				
			}
			}
		}
	}

	private void addScore(int i, int s) {
		scores[i]+= s;
		broadcast("SCORE " + i + " " + scores[i]);
	}

	public void group_collisions(Pacman pacman, Ghost[] ghosts) {

	}

	public void group_group_collisions(Character[] characters, Drawable[] superballs) {

	}

	public void in_group_collisions(Ghost[] ghosts) {

	}

	public void broadcast(String message) {
		System.out.println(listeners.size());
		for (ServerThread listener : listeners)
			listener.send(message);
	}

	public String getMapAsString() {
		String mapstring = "";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				mapstring += map[i][j] + " ";
			}
			mapstring += "ELN";
		}
		return mapstring;
	}

	public void update() {
		for (int i = 0; i < characters.size(); i++) {
			Character c = characters.get(i);
			if (c.update()) {
				broadcast("MOVE " + i + " " + c.getPosX() + " " + c.getPosY() + " " + c.getDesX() + " " + c.getDesY());
				collisionsBalls(c, i);
				collisions(c,i);
			}
			
		}
	}

	public void setMovement(int profile, int dir) {
		if (profile == -1)
			return;
		System.out.println("c: " + profile + " " + dir);
		this.characters.get(profile).setDir(dir);
	}

	public int getPath(int x, int y) {
		int i = y / 50;
		i = (i >= map.length) ? map.length - 1 : ((i <= 0) ? 0 : i);
		int j = x / 50;
		j = (j >= map[0].length) ? map[0].length - 1 : ((j <= 0) ? 0 : j);
		return map[i][j] / 8;
	}
	/*
	private String createScoresString() {
		StringBuffer str = new StringBuffer("SCORES ");
		str.append((this.totalballs - this.balls.size()) + "/" + this.totalballs);
		for (int i = 1; i < characters.size(); i++) {
			Ghost g = (Ghost) characters.get(i);
			str.append(" " + g.getPacmansKilled() + "-" + g.getGhostsKilled());
		}
		return str.toString();
	}*/
/*
	public void sendScores() {
		broadcast(createScoresString());
	}*/
/*
	public void sendFinalScores() {
		int pos = -1;
		if (this.totalballs - this.balls.size() == 0)
			pos = 0;
		else {
			int max = ((Ghost) characters.get(1)).getGhostsKilled();
			pos = 1;
			for (int i = 2; i < characters.size(); i++) {
				Ghost g = (Ghost) characters.get(i);
				if (g.getGhostsKilled() > max) {
					max = g.getGhostsKilled();
					pos = i;
				}
			}
		}
		broadcast("WINNER " + pos + " " + createScoresString());
	}
*/
	public Point getRespawnPoint() {
		ArrayList<Point> points = new ArrayList<Point>();
		boolean[][] mat = new boolean[3][2];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++)
				if (map[i][j] % 2 == 1)
					points.add(new Point(i * 50 + 10, j * 50 + 10));

		int x = 0, y = 0;
		for (int g = 1; g < this.characters.size(); g++) {
			int a, b;
			x += Math.floorDiv(this.characters.get(g).getPosX(), 50);
			y += Math.floorDiv(this.characters.get(g).getPosY(), 50);
			if (Math.floorDiv(this.characters.get(g).getPosY(), 50) < 4)
				a = 0;
			else if (Math.floorDiv(this.characters.get(g).getPosY(), 50) > 7)
				a = 2;
			else
				a = 1;
			if (Math.floorDiv(this.characters.get(g).getPosX(), 50) < 5)
				b = 0;
			else
				b = 1;
			mat[a][b] = true;
		}

		for (int k = 0; k < points.size(); k++) {
			int a, b;
			if (Math.floorDiv((int) points.get(k).getY(), 50) < 4)
				a = 0;
			else if (Math.floorDiv((int) points.get(k).getY(), 50) > 7)
				a = 2;
			else
				a = 1;
			if (Math.floorDiv((int) points.get(k).getX(), 50) < 5)
				b = 0;
			else
				b = 1;
			if (mat[a][b])
				points.remove(k);
		}

		Point cm = new Point(x / this.characters.size(), y / this.characters.size());
		double dist = getDistancia(cm, points.get(0));
		int pos = 0;
		for (int k = 1; k < points.size(); k++) {
			if (getDistancia(cm, points.get(k)) < dist) {
				dist = getDistancia(cm, points.get(k));
				pos = k;
			}
		}
		return points.get(pos);
	}

	private double getDistancia(Point a, Point b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
}

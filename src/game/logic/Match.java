package game.logic;

import java.util.ArrayList;

import game.map.MapReader;
import server.config.Config;
import server.conn.ServerThread;


public class Match {
	private int time = 0;
	private int[][] map;
	private ArrayList<Character> characters;
	private ArrayList<Drawable> balls;
	private ArrayList<Drawable> superballs;
	private static Match INSTANCE = null;
	private boolean playing = false;
	private ArrayList<ServerThread> listeners = null;
	
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
	}

	public static Match getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Match();
		return INSTANCE;
	}

	public int addCharacter() {
		int x, y, w, h, v, l, p;
		if(characters.isEmpty()){
			System.out.println("Pacman");
			x = Integer.parseInt(Config.get("pacman_posX"));
			y = Integer.parseInt(Config.get("pacman_posY"));
			w = Integer.parseInt(Config.get("pacman_width"));
			h = Integer.parseInt(Config.get("pacman_height"));
			v = Integer.parseInt(Config.get("pacman_vel"));
			l = Integer.parseInt(Config.get("pacman_lifespan"));
			p = Integer.parseInt(Config.get("pacman_powerspan"));
			System.out.println(x+" "+y+" "+w+" "+h+" "+v+" "+l+" "+p);
			characters.add(new Pacman(x,y,w,h,v,l,p));
			
			return 0;
		}
		if(characters.size() < 4){
			System.out.println("Ghost");
			x = Integer.parseInt(Config.get("ghost_posX"))+40*characters.size()-1;
			y = Integer.parseInt(Config.get("ghost_posY"));
			w = Integer.parseInt(Config.get("ghost_width"));
			h = Integer.parseInt(Config.get("ghost_height"));
			v = Integer.parseInt(Config.get("ghost_vel"));
			l = Integer.parseInt(Config.get("ghost_lifespan"));
			p = Integer.parseInt(Config.get("ghost_powerspan"));

			System.out.println(x+" "+y+" "+w+" "+h+" "+v+" "+l+" "+p);
			characters.add(new Ghost(x,y,w,h,v,l,p));
			
			return characters.size()-1;
		}
		return -1;
	}

	

	public void addListener(ServerThread client){
		this.listeners.add(client);
	}
	
	public void start() {
		StringBuffer msg = new StringBuffer("START ");
		for(int i = 0; i < characters.size(); i++){
			Character c = characters.get(i);
			msg.append(c.getPosX() + " " + c.getPosY() + " " + 
					c.getWidth() + " " + c.getHeight() + " " + 
					c.getVel() + " " + c.getLifeSpan() + " " +
					c.getPowerSpan() + "ELN"); 
		}
		String finalstring = msg.toString();
		broadcast(finalstring.substring(0, finalstring.length()-3));
		time = 0;
		playing = true;
	}
	
	public void finish(){
		INSTANCE = null;
		playing = false;
		listeners = null;
	}

	public boolean isFinished() {
		if(time >= 60) playing = false;
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
	
	
	
	public void collisions(Object objA, Object objB) {
	
		int dist;
		int radios;
		if(objA.getClass()==Pacman.class && objB.getClass() == Ghost.class){
			Pacman cA = (Pacman)objA;
			Ghost cB = (Ghost)objB;
			dist = (cA.posX+cA.width/2)*(cA.posX+cA.width/2)+(cA.posY+cA.height/2)+cA.posY+cA.height/2 
				    - (cB.posX+cB.width/2)*(cB.posX+cB.width/2)+(cB.posY+cB.height/2)+cB.posY+cB.height/2;
			radios = cA.height/2 + cB.height/2;
			if(dist - radios <=0){
				if(cA.getPower()!=1){
					cB.morir();
					cA.killedGhost();
				}
				else{
					cA.morir();
					cB.killedPacman();
				}
				
			}
		}
		if(objB.getClass()==Pacman.class && objA.getClass() == Ghost.class){
			Pacman cB = (Pacman)objB;
			Ghost cA = (Ghost)objA;
			dist = (cA.posX+cA.width/2)*(cA.posX+cA.width/2)+(cA.posY+cA.height/2)+cA.posY+cA.height/2 
				    - (cB.posX+cB.width/2)*(cB.posX+cB.width/2)+(cB.posY+cB.height/2)+cB.posY+cB.height/2;
			radios = cA.height/2 + cB.height/2;
			if(dist - radios <=0){
				if(cB.getPower()!=-1){
					cA.morir();
					cB.killedGhost();
				}
				else{
					cB.morir();
					cA.killedPacman();
				}
				
			}
		
		}
		
		
		
			
			
		
	}
	
	public void group_collisions(Pacman pacman, Ghost[] ghosts) {

	}

	public void group_group_collisions(Character[] characters, Drawable[] superballs) {

	}

	public void in_group_collisions(Ghost[] ghosts) {

	}

	public void broadcast(String message){
		System.out.println(listeners.size());
		for(ServerThread listener : listeners)
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
		for(int i = 0; i<characters.size(); i++){
			Character c = characters.get(i);
			if(c.update()){
				broadcast("MOVE "+i+" "+c.getPosX()+" "+c.getPosY()+" "+c.getDesX()+" "+c.getDesY());
			}
		}
	}

	public void setMovement(int profile, int dir) {
		if(profile==-1) return;
		this.characters.get(profile).setDir(dir);
	}

	public int getPath(int x, int y){
		int i = y / 50;
		i = (i >= map.length) ? map.length - 1 : ((i <= 0) ? 0 : i);
		int j = x / 50;
		j = (j >= map[0].length) ? map[0].length - 1 : ((j <= 0) ? 0 : j);
		return map[i][j] / 8;
	}
}

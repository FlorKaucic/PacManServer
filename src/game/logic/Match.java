package game.logic;

import java.util.ArrayList;

import game.map.MapReader;
import server.config.Config;

public class Match {
	private long tIni = 0;
	private int[][] map;
	private ArrayList<Character> characters;
	private ArrayList<Drawable> balls;
	private ArrayList<Drawable> superballs;
	private static Match INSTANCE = null;
	private boolean playing = false;

	private Match() {
		characters = new ArrayList<Character>();
		balls = new ArrayList<Drawable>();
		superballs = new ArrayList<Drawable>();
		map = MapReader.read("res/map/map_0.in");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int bolita = Math.floorDiv(Math.floorMod(map[i][j], 8), 2);
				if (bolita == 1)
					this.balls.add(new Drawable(j * 50 + 19, i * 50 + 19, 8, 8));
				if (bolita == 2)
					this.superballs.add(new Drawable(j * 50 + 15, i * 50 + 15, 15, 15));
			}
		}
		System.out.println("Creada");
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

			System.out.println(x+y+w+h+v);
			characters.add(new Pacman(x,y,w,h,v,l,p));
			return 0;
		}
		if(characters.size() < 4){
			System.out.println("Ghost");
			x = Integer.parseInt(Config.get("ghost_posX"));
			y = Integer.parseInt(Config.get("ghost_posY"));
			w = Integer.parseInt(Config.get("ghost_width"));
			h = Integer.parseInt(Config.get("ghost_height"));
			v = Integer.parseInt(Config.get("ghost_vel"));
			l = Integer.parseInt(Config.get("pacman_lifespan"));
			p = Integer.parseInt(Config.get("pacman_powerspan"));

			System.out.println(x+y+w+h+v);
			characters.add(new Ghost(x,y,w,h,v,l,p));
		}
		return -1;
	}

	public void start() {
		tIni = System.currentTimeMillis();
		playing = true;
	}

	public boolean isFinished() {
		if(getTimePassed() >= 60)
			playing = false;
		return !playing;
	}

	public long getTimePassed() {
		if (!playing)
			return 0;
		return  (int) (System.currentTimeMillis() - tIni) / 1000;
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

	public void collisions(Object objA, Object objB) {

	}

	public void group_collisions(Pacman pacman, Ghost[] ghosts) {

	}

	public void group_group_collisions(Character[] characters, Drawable[] superballs) {

	}

	public void in_group_collisions(Ghost[] ghosts) {

	}

	

}

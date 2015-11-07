package game.logic;

import game.map.MapReader;

public class Match {
	private long tIni;
	//	private Character [] personajes;
	//	private Drawable [] mapTiles;
	private int[][] map;
	//	private Drawable [] bolitas;
	private static Match INSTANCE = null;

	private Match() {
		map = MapReader.read("res/borrar/map_0.in");
	}

	public Match getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Match();
		return INSTANCE;
	}
	
	public String getMapAsString(){
		return ""; // CHANGE
	}

	public void colisiones() {

	}

	public void terminar() {

	}

}

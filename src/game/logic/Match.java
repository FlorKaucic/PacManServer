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
		map = MapReader.read("res/map/map_0.in");
	}

	public static Match getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Match();
		return INSTANCE;
	}

	public void start(){
		tIni = System.currentTimeMillis();
	}
	
	public String getMapAsStrings(){
		String mapstring = "";
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				mapstring += map[i][j] + " ";
			}
			mapstring += "ELN";
		}
		return mapstring;
	}

	public void colisiones() {

	}

	public void terminar() {

	}

}

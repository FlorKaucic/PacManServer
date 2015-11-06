package game.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MapReader {

	public static int[][] read(String ruta) {
		int[][] map = null;
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String linea;
			String[] data;
			int nf = 0;

			file = new File(ruta);
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			linea = br.readLine();
			int f = Integer.parseInt(linea.split(" ")[0]);
			int c = Integer.parseInt(linea.split(" ")[1]);
			map = new int[f][c];

			while ((linea = br.readLine()) != null) {
				data = linea.split(" ");
				for (int i = 0; i < c; i++)
					map[nf][i] = Integer.parseInt(data[i]);
				nf++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;
	}
}

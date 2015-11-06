package server.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Config {
	private static Properties CONFIG;
	
	public static void load(){
		CONFIG = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("sconfig.properties");
			CONFIG.load(input);
			CONFIG.put("ip",InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se puede cargar properties.", "Servidor", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String get(String key){
		return (String) CONFIG.get(key);
	}
	
	public static void set(String key, String value){
		CONFIG.put(key, value);
	}
	
}

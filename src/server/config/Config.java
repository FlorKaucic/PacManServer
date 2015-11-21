package server.config;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			
			CONFIG.put("screen_width", String.valueOf(screenSize.width));
			CONFIG.put("screen_height", String.valueOf(screenSize.height));
			CONFIG.put("taskbar_height", String.valueOf(screenSize.height - winSize.height));
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
	
	public static void save(){
		OutputStream output = null;
		
		try {
			output = new FileOutputStream("sconfig.properties");
			CONFIG.store(output, null);
		} catch (IOException io) {
			JOptionPane.showMessageDialog(null, "No se pudieron guardar los cambios en la configuracion.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (output != null) {
				try {
					output.close();
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

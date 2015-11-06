package server.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import server.config.Config;

public class Server extends Thread {
	private int port = 0;
	private InetAddress ip = null;
	private ServerSocket serverSocket = null;
	private String errorMessage;

	public Server(String ip, int port) throws UnknownHostException {
		this.port = port;
		this.ip = InetAddress.getByName(ip);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void changePort() {
		this.port++;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				serverSocket = new ServerSocket(this.port, 100, this.ip);
				errorMessage = null;
				//System.out.println("Conectado " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
				JOptionPane.showMessageDialog(null, "Conectado " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort(), "Servidor", JOptionPane.INFORMATION_MESSAGE);
				Config.set("port", String.valueOf(this.port));
				//Modificar esto para que sea con un getInstance
				//ServerFrame.changeServerStatus();
				break;
			} catch (IOException e) {
				errorMessage = "Error con el puerto " + this.port + ".";
				JOptionPane.showMessageDialog(null, "Corte create: "+ errorMessage, "Servidor", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				this.stopServer();
				this.changePort();
			}
		}
		Socket clientSocket = null;
		try {
			while (true) {
				clientSocket = serverSocket.accept();
				Thread t = new ServerThread(clientSocket);
				t.start();
				System.out.println("Nuevo client");
				errorMessage = null;
			}
		} catch (IOException e) {
			errorMessage = "Error al conectar cliente.";
			JOptionPane.showMessageDialog(null, "Corte cliente", "Servidor", JOptionPane.ERROR_MESSAGE);
			this.stopServer();
		}

	}

	public void stopServer() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				serverSocket = null;
				JOptionPane.showMessageDialog(null, "Desconectado", "Servidor", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				errorMessage = "No se pudo cerrar el servidor";
				JOptionPane.showMessageDialog(null, "Corte stop", "Servidor", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}

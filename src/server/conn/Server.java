package server.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
				System.out.println("Conectado " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
				Config.set("port", String.valueOf(this.port));
				break;
			} catch (IOException e) {
				errorMessage = "Error al conectar con el puerto " + this.port + ".";
				System.out.println(errorMessage);
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
				System.out.println("Nuevo cliente");
				errorMessage = null;
			}
		} catch (IOException e) {
			errorMessage = "Error al conectar cliente.";
			System.out.println(errorMessage);
			this.stopServer();
		}

	}

	public void stopServer() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				serverSocket = null;
				System.out.println("Desconectado");
			} catch (IOException e) {
				errorMessage = "No se pudo cerrar el servidor";
				System.out.println(errorMessage);
			}
		}
	}

}

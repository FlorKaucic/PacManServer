package server.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	public static int PORT = 0;
	public static InetAddress IP = null;
	private ServerSocket serverSocket = null;
	private String errorMessage = null;

	public Server(InetAddress ip, int port) {
		Server.PORT = port;
		Server.IP = ip;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void changePort() {
		Server.PORT++;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(Server.PORT, 100, Server.IP);
			errorMessage = null;
			System.out.println("Conectado " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
		} catch (IOException e) {
			errorMessage = "Error con el puerto " + Server.PORT + ".";
			System.out.println("Corte create");
			e.printStackTrace();
			this.stopServer();
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
			System.out.println("Corte cliente");
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
				System.out.println("Corte stop");
			}
		}
	}

}

package conn.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer extends Thread {
	private int port = 4444;
	private ServerSocket serverSocket = null;
	private String errorMessage = null;

	public void changePort() {
		port++;
	}

	public int getPort() {
		return port;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			errorMessage = null;
			System.out.println("Conectado " + serverSocket.getInetAddress());
		} catch (IOException e) {
			errorMessage = "Error con el puerto " + port + ".";
			System.out.println("Corte create");
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

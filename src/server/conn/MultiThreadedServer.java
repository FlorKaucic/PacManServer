package server.conn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(null, "Conectado " + serverSocket.getInetAddress(), "Servidor", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			errorMessage = "Error con el puerto " + port + ".";
			JOptionPane.showMessageDialog(null, "Corte create: " + errorMessage, "Servidor", JOptionPane.ERROR_MESSAGE);
			this.stopServer();
		}

		Socket clientSocket = null;
		try {
			while (true) {
				clientSocket = serverSocket.accept();
				Thread t = new ServerThread(clientSocket);
				t.start();
				JOptionPane.showMessageDialog(null, "Nuevo cliente", "Servidor", JOptionPane.INFORMATION_MESSAGE);
				errorMessage = null;
			}
		} catch (IOException e) {
			errorMessage = "Error al conectar cliente.";
			JOptionPane.showMessageDialog(null, "Corte cliente: " + errorMessage, "Servidor", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "Corte Stop: " + errorMessage, "Servidor", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

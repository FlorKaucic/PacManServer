package server.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import game.logic.User;
import server.conn.alert.ClientAlert;

public class ServerThread extends Thread {
	Socket clientSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	User user;
	int profile = -1;
	
	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine;

			out.println("CONNECTED");

			while ((inputLine = in.readLine()) != null) {
				outputLine = ServerProtocol.processInput(this, inputLine);
				out.println(outputLine);
			}
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			if(this.profile!=-1){
				JOptionPane.showMessageDialog(null, "Se fue un jugador de la partida."
						+ "\nSe cerrara el servidor.", "Partida", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			try {
				in.close();
				clientSocket.close();
			} catch (IOException e1) {
				ClientAlert dialog = new ClientAlert("Se desconecto un cliente.");
				dialog.setVisible(true);
				return;
			}
			ClientAlert dialog = new ClientAlert("Se desconecto un cliente.");
			dialog.setVisible(true);
		}
	}

	public void send(String message) {
		out.println(message);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProfile(int profile) {
		this.profile = profile;
	}
}

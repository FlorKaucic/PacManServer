package server.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.conn.alert.ClientAlert;

public class ServerThread extends Thread {
	Socket clientSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;

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
				outputLine = ServerProtocol.processInput(inputLine);
				out.println(outputLine);
			}
			in.close();
			clientSocket.close();
		} catch (IOException e) {
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
}

package server.conn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerThread extends Thread {
	Socket clientSocket = null;
	
	public ServerThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine;

			out.println("Conectado.");

			while ((inputLine = in.readLine()) != null) {
				outputLine = ServerProtocol.processInput(inputLine);
				out.println(outputLine);
//				if (inputLine.equals("fin")){
//					System.out.println("Se desconecta un cliente.");
//					break;
//				}
			}
			in.close();
			clientSocket.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se puede conectar con el cliente", "Servidor", JOptionPane.ERROR_MESSAGE);
		}
	}
}

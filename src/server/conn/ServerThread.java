package server.conn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket clientSocket = null;
	
	public ServerThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//			String inputLine, outputLine;

			out.println("Conectado.");

//			while ((inputLine = in.readLine()) != null) {
//				outputLine = "Se recibio: " + inputLine;
//				System.out.println(outputLine);
//				out.println(outputLine);
//				if (inputLine.equals("fin")){
//					System.out.println("Se desconecta un cliente.");
//					break;
//				}
//			}
//			in.close();
			clientSocket.close();
		} catch (Exception e) {
			System.err.println("Fallo.");
		}
	}
}

package conn.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4444);
			System.out.println("ServerSocket creado.");
		} catch (IOException e) {
			System.err.println("No se puede usar el puerto: 4444.");
			System.exit(1);
		}

		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
			System.out.println("Se escucho un cliente.");
		} catch (IOException e) {
			System.err.println("Fallo.");
			System.exit(1);
		}

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine;

			out.println("Conectado.");
			
			while ((inputLine = in.readLine()) != null) {
				outputLine = "Se recibio: " + inputLine;
				System.out.println(outputLine);
				out.println(outputLine);
				if (inputLine.equals("fin"))
					break;
			}
			in.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Fallo.");
			System.exit(1);
		}
	}
}

package conn.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
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
			while(true){
				clientSocket = serverSocket.accept();
				System.out.println("Se escucho un cliente.");
				Thread t = new ServerThread(clientSocket);
			    t.start();
		    }
		} catch (IOException e) {
			System.err.println("Fallo.");
			System.exit(1);
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Fallo.");
			System.exit(1);
		}
	}
}

package server.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import game.logic.User;
import server.conn.alert.ClientAlert;

public class ServerThread extends Thread {
	PrintWriter out = null;
	BufferedReader in = null;
	User user;
	int profile = -1;
	
	public ServerThread(Socket clientSocket) {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			if(this.profile!=-1){
				System.exit(0);
			}
			ClientAlert dialog = new ClientAlert("Se desconecto un cliente.");
			dialog.setVisible(true);
		}
	}

	@Override
	public void run() {
		try {
			String inputLine, outputLine;
			out.println("CONNECTED");
			while ((inputLine = in.readLine()) != null) {
				outputLine = ServerProtocol.processInput(this, inputLine);
				out.println(outputLine);
			}
		} catch (IOException e) {
			if(this.profile!=-1){
				System.exit(0);
			}
			ClientAlert dialog = new ClientAlert("Se desconecto un cliente.");
			dialog.setVisible(true);
		}
	}

	public void send(String message) {
		try{
			out.println(message);
		}catch(Exception e){
			if(this.profile!=-1){
				System.exit(0);
			}
			ClientAlert dialog = new ClientAlert("No se pudo comunicar.");
			dialog.setVisible(true);
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProfile(int profile) {
		this.profile = profile;
	}
	
	public int getProfile(){
		return this.profile;
	}
}

package game.logic.match;

import javax.swing.JOptionPane;

import game.logic.Match;
import server.gui.ServerFrame;

public class MatchHandler extends Thread {
	private ServerFrame caller;
	
	public void run(){
		Match match = Match.getInstance();
		while(!match.isFinished()){
			System.out.println("Todavia no termino... "+match.getTimePassed());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error de ejecucion.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		System.out.println("Partida terminada. "+match.getTimePassed());
		caller.setFinishedMatch();
	}	
	
	public void setCaller(ServerFrame caller){
		this.caller = caller;
	}
	
}

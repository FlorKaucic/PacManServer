package game.logic.match;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import game.logic.Match;
import server.gui.ServerFrame;

public class MatchHandler extends Thread {
	private ServerFrame caller;

	public void run() {
		Match match = Match.getInstance();
		while (!match.isPlaying()) {
			if (match.getNumberOfCharacters() == 3)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error de ejecucion.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
//		try {
//			Thread.sleep(1500);
//		} catch (InterruptedException e) {
//			JOptionPane.showMessageDialog(null, "Error de ejecucion.", "Error", JOptionPane.ERROR_MESSAGE);
//			System.exit(0);
//		}
//		match.broadcast("COUNTDOWN");
		Timer t = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				match.start();
				int count = 0;
				while (!match.isFinished()) {
					match.update();
					if(count==40)
						match.setTime();
					count++;
					try {
						Thread.sleep(25);
					} catch (InterruptedException ex) {
						JOptionPane.showMessageDialog(null, "Error de ejecucion.", "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
				System.out.println("Partida terminada");
				caller.setFinishedMatch();
			}
		});
		t.start();
	}

	public void setCaller(ServerFrame caller) {
		this.caller = caller;
	}

}

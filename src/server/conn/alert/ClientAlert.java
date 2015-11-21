package server.conn.alert;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

import server.config.Config;

@SuppressWarnings("serial")
public class ClientAlert extends JDialog {
	protected JLabel lblTime;
	
	public ClientAlert(String msg) {
		setLayout(null);
		setSize(250, 50);
		int x = Integer.parseInt(Config.get("screen_width"))-300;
		int y = Integer.parseInt(Config.get("screen_height"))-Integer.parseInt(Config.get("taskbar_height"))-100;
		setLocation(x, y);
		this.setUndecorated(true);
		setModalityType(ModalityType.MODELESS);
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.GRAY, 2, false));
		lblTime = new JLabel();
		lblTime.setBounds(20, 7, 200, 30);
		lblTime.setText(msg);
		lblTime.setForeground(Color.BLACK);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblTime);
		
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClientAlert.this.setVisible(false);
				ClientAlert.this.dispose();
			}
		});
		timer.start();
	}

	public void setBorderColor(Color color) {
		getRootPane().setBorder( BorderFactory.createLineBorder(color, 2, false));
	}
}
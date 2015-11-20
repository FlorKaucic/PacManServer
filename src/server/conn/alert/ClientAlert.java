package server.conn.alert;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class ClientAlert extends JDialog {
	protected JLabel lblTime;
	
	public ClientAlert(String msg) {
		setLayout(null);
		setSize(250, 100);
		setLocation(50, 50);
		setModalityType(ModalityType.MODELESS);
		setBackground(Color.BLACK);
		setTitle("Notificacion");
		lblTime = new JLabel();
		lblTime.setText(msg);
		lblTime.setForeground(Color.BLACK);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(20, 0, 200, 50);
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
	
	
}
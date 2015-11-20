package server.conn.alert;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ClientAlert extends JDialog {
	protected JLabel lblTime;
	
	public ClientAlert(String msg) {
		setLayout(null);
		setSize(200, 50);
		setModalityType(ModalityType.MODELESS);
		setBackground(Color.BLACK);
		setTitle("Notificacion");
		lblTime = new JLabel();
		lblTime.setText(msg);
		lblTime.setForeground(Color.BLACK);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(10, 0, 200, 50);
		add(lblTime);
	}
}
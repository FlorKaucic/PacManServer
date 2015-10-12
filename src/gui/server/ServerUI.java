package gui.server;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conn.server.MultiThreadedServer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class ServerUI extends JFrame {
	private MultiThreadedServer server = null;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int res = JOptionPane.showConfirmDialog(ServerUI.this, "Si prosigue, se desconectará el servidor.\n¿Desea continuar?", "Salir", JOptionPane.OK_CANCEL_OPTION);
				if(res==JOptionPane.OK_OPTION){
					System.exit(1);
				}
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2-200, dim.height/2-225, 400, 450);
		setTitle("Servidor");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLayout(null);
		
		JButton btnConectarServidor = new JButton("Conectar servidor");
		btnConectarServidor.setBounds(105, 65, 180, 40);
		
		JButton btnDesconectarServidor = new JButton("Desconectar servidor");
		btnDesconectarServidor.setBounds(105, 115, 180, 40);
		
		JButton btnCrearPartida = new JButton("Crear partida");
		btnCrearPartida.setBounds(105, 225, 180, 40);
		
		JButton btnVerPartida = new JButton("Ver partida");
		btnVerPartida.setBounds(105, 275, 180, 40);
		
		JButton btnGestionarUsuarios = new JButton("Gestionar usuarios");
		btnGestionarUsuarios.setBounds(105, 347, 180, 40);

		//Labels
		JLabel lblEstadoServidor = new JLabel("Estado servidor: Desconectado.");
		lblEstadoServidor.setBounds(70, 15, 250, 40);
		lblEstadoServidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblEstadoServidor);
		
		JLabel lblEstadoPartida = new JLabel("Estado partida: No hay partida.");
		lblEstadoPartida.setBounds(70, 175, 250, 40);
		lblEstadoPartida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblEstadoPartida);
		
		//ActionListeners
		btnConectarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Conectar Servidor
				server = new MultiThreadedServer();
				do{
					server.start();
					if(server.getErrorMessage()!=null)
						server.changePort();
				}while(server.getErrorMessage()!=null);
				btnDesconectarServidor.setEnabled(true);
				btnCrearPartida.setEnabled(true);
				lblEstadoServidor.setText("Estado servidor: Conectado.");
				btnConectarServidor.setEnabled(false);
			}
		});
		contentPane.add(btnConectarServidor);
		
		btnDesconectarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Desconectar Servidor
				server.stopServer();
				btnConectarServidor.setEnabled(true);
				btnCrearPartida.setEnabled(false);
				btnVerPartida.setEnabled(false);
				lblEstadoServidor.setText("Estado servidor: Desconectado.");
				lblEstadoPartida.setText("Estado partida: No hay partida.");
				btnDesconectarServidor.setEnabled(false);
			}
		});
		contentPane.add(btnDesconectarServidor);
		
		btnCrearPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Crear Partida
				btnVerPartida.setEnabled(true);
				lblEstadoPartida.setText("Estado partida: Partida en juego.");
				btnCrearPartida.setEnabled(false);
			}
		});
		contentPane.add(btnCrearPartida);
		
		btnVerPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnVerPartida);
		
		btnGestionarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnGestionarUsuarios);
	
		btnDesconectarServidor.setEnabled(false);
		btnCrearPartida.setEnabled(false);
		btnVerPartida.setEnabled(false);
	}
}

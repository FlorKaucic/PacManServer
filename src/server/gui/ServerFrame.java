package server.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import game.logic.Match;
import game.logic.match.MatchHandler;
import server.config.Config;
import server.conn.Server;
import server.gui.UserMgrFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;

@SuppressWarnings("serial")
public class ServerFrame extends JFrame {
	private Server server = null;

	private JPanel contentPane;
	private JButton btnConectarServidor;
	private JButton btnDesconectarServidor;
	private JButton btnCrearPartida;
	private JButton btnVerPartida;
	private JButton btnGestionarUsuarios;
	private JLabel lblDatosServidor;
	private JLabel lblEstadoServidor;
	private JLabel lblEstadoPartida;
	
	public static void main(String[] args) {

		Config.load();
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
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
	public ServerFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int res = JOptionPane.showConfirmDialog(ServerFrame.this,
						"Si prosigue, se desconectar\u00E1 el servidor.\n�Desea continuar?", "Salir",
						JOptionPane.OK_CANCEL_OPTION);
				if (res == JOptionPane.OK_OPTION) {
					System.exit(1);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		int x = Integer.parseInt(Config.get("screen_width"))/2-200;
		int y = Integer.parseInt(Config.get("screen_height"))/2-250;
		setBounds(x, y, 400, 500);
		setTitle("Servidor");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLayout(null);

		btnConectarServidor = new JButton("Conectar servidor");
		btnConectarServidor.setBounds(105, 115, 180, 40);

		btnDesconectarServidor = new JButton("Desconectar servidor");
		btnDesconectarServidor.setBounds(105, 165, 180, 40);

		btnCrearPartida = new JButton("Crear partida");
		btnCrearPartida.setBounds(105, 275, 180, 40);

		btnVerPartida = new JButton("Ver partida");
		btnVerPartida.setBounds(105, 335, 180, 40);

		btnGestionarUsuarios = new JButton("Gestionar usuarios");
		btnGestionarUsuarios.setBounds(105, 397, 180, 40);

		//Labels
		lblDatosServidor = new JLabel("");
		lblDatosServidor.setBounds(70, 5, 250, 40);
		lblDatosServidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblDatosServidor);

		lblEstadoServidor = new JLabel("Estado servidor: Desconectado.");
		lblEstadoServidor.setBounds(70, 55, 250, 40);
		lblEstadoServidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblEstadoServidor);

		lblEstadoPartida = new JLabel("Estado partida: No hay partida.");
		lblEstadoPartida.setBounds(70, 235, 250, 40);
		lblEstadoPartida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblEstadoPartida);

		//ActionListeners
		btnConectarServidor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Conectar Servidor
				try {
					server = new Server(Config.get("ip"), Integer.parseInt(Config.get("port")));
				} catch (UnknownHostException e1) {
					JOptionPane.showMessageDialog(null, "No se puede conectar servidor", "Servidor", JOptionPane.ERROR_MESSAGE);				
				}
				server.start();					
				ServerFrame.this.changeServerStatus();
			}
		});
		contentPane.add(btnConectarServidor);

		btnDesconectarServidor.addActionListener(new ActionListener() {
			@Override
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
			@Override
			public void actionPerformed(ActionEvent e) {
				//Crear Partida
				btnVerPartida.setEnabled(true);
				Match.getInstance();
				MatchHandler t = new MatchHandler();
				t.setCaller(ServerFrame.this);
				t.start();
				lblEstadoPartida.setText("Estado partida: Partida en juego.");
				btnCrearPartida.setEnabled(false);
			}
		});
		contentPane.add(btnCrearPartida);

		btnVerPartida.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("java -jar PacManClient.jar VIEWER");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "No se pudo abrir un visor de la partida.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnVerPartida);

		btnGestionarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserMgrFrame usrm = new UserMgrFrame();
				usrm.setVisible(true);
			}
		});
		contentPane.add(btnGestionarUsuarios);

		btnDesconectarServidor.setEnabled(false);
		btnCrearPartida.setEnabled(false);
		btnVerPartida.setEnabled(false);
	}
	
	public void changeServerStatus(){
		btnDesconectarServidor.setEnabled(true);
		btnCrearPartida.setEnabled(true);
		lblDatosServidor.setText("Servidor: " + Config.get("ip") + ":" + Config.get("port"));
		lblEstadoServidor.setText("Estado servidor: Conectado.");
		btnConectarServidor.setEnabled(false);
	}

	public void setFinishedMatch() {
		btnCrearPartida.setEnabled(true);
		btnVerPartida.setEnabled(false);
	}
}

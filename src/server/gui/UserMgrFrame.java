package server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.logic.User;
import server.persistence.UserDAO;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class UserMgrFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Object[][] data = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMgrFrame frame = new UserMgrFrame();
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
	public UserMgrFrame() {
		setTitle("PacMan - Usuarios");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		String[] nombreColumnas = { "ID", "Usuario", "Habilitado"};
		try{
		User[] users = UserDAO.getAll();
		int i=0;
		
		for(User u : users){
			data[i][0] = new Integer(u.getId());
			data[i][1] = u.getUsername();
			data[i][2] = new Boolean(u.isEnabled());
		}
		}catch(SQLException e){
		JOptionPane.showMessageDialog(null, "No se pudo obtener la lista de usuarios", "Gestión de usuarios",
				JOptionPane.ERROR_MESSAGE);
		}
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 422, 244);
		contentPane.add(scrollPane);

		table = new JTable(data, nombreColumnas);
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setBackground(Color.BLACK);
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
	}

}
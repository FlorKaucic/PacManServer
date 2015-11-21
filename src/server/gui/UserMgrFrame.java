package server.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.config.Config;
import game.logic.User;
import server.config.UserTableModel;
import server.persistence.UserDAO;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class UserMgrFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Object[][] data = null;
	private int cantUsers=0;
	@SuppressWarnings("unused")
	private boolean [] changes;
	UserTableModel model;
	private JButton btnGuardarCambios;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int res;
				if(model.isThereChanges()){
					res = JOptionPane.showConfirmDialog(UserMgrFrame.this,
						"¿Desea salir sin guardar los cambios?", "Salir",
						JOptionPane.OK_CANCEL_OPTION);
					if (res == JOptionPane.OK_OPTION) {
						UserMgrFrame.this.dispose();
					}
				}
				else
					UserMgrFrame.this.dispose();
				
			}
		});
		setTitle("PacMan - Usuarios");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		int x = Integer.parseInt(Config.get("screen_width"))/2-244;
		int y = Integer.parseInt(Config.get("screen_height"))/2-275;
		setBounds(x, y, 488, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		try{
			User[] users = UserDAO.getAll();
			
			data = new Object[users.length][3];
			for(User u : users){
				data[cantUsers][0] = new Integer(u.getId());
				data[cantUsers][1] = u.getUsername();
				data[cantUsers][2] = new Boolean(u.isEnabled());
				cantUsers++;
			}
			}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "No se pudo obtener la lista de usuarios", "Gestión de usuarios",
					JOptionPane.ERROR_MESSAGE);
			}
		
		changes = new boolean[data.length];
		model = new UserTableModel(data);
        contentPane.setLayout(null);
        table = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 11, 452, 427);
		scrollPane.setViewportView(table);
		getContentPane().add(scrollPane);
		table.setBackground(Color.BLACK);
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		
		btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean [] changes;
				if(model.isThereChanges()){
					changes=model.getChanges();
					int [] idlistE = new int [model.getCantChanges()];
					int [] idlistD = new int [model.getCantChanges()];
					int indE=0, indD=0;
					for(int i=0;i<idlistE.length;i++){
						idlistE[i]=-1;
						idlistD[i]=-1;
					}
						
					for(int i = 0; i<changes.length;i++){
						if(changes[i]){
							if((boolean) data[i][2]){
								idlistE[indE] = i+1;
								indE++;
							}
							else{
								idlistD[indD]=i+1;
								indD++;
							}
						}
					}
					
					try {
						if(idlistD[0]!=-1)
							UserDAO.disable(idlistD);
							
						if(idlistE[0]!=-1)
							UserDAO.enable(idlistE);
					
						model.setChange();
						JOptionPane.showMessageDialog(null, "Cambios guardados correctamente", "Gestión de usuarios",
								JOptionPane.INFORMATION_MESSAGE);
						UserMgrFrame.this.dispose();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "No se pudo guardar los cambios en los usuarios.", "Gestión de usuarios",
								JOptionPane.ERROR_MESSAGE);
						
					}
					
				}
				
			}
		});
		btnGuardarCambios.setBounds(316, 478, 146, 23);
		contentPane.add(btnGuardarCambios);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		
		
		//this.pack();
	}
	
	
}



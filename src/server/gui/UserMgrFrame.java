package server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

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
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 422, 244);
		contentPane.add(scrollPane);

		table = new JTable(new MyTableModel());
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setBackground(Color.BLACK);
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
	}
	
	class MyTableModel extends AbstractTableModel {
		String[] columnNames = { "ID", "Usuario", "Habilitado"};
		private Object[][] data;
		
		public MyTableModel(){
			try{
				User[] users = UserDAO.getAll();
				int i=0;
				data = new Object[users.length][3];
				for(User u : users){
					data[i][0] = new Integer(u.getId());
					data[i][1] = u.getUsername();
					data[i][2] = new Boolean(u.isEnabled());
					i++;
				}
				}catch(SQLException e){
				JOptionPane.showMessageDialog(null, "No se pudo obtener la lista de usuarios", "Gestión de usuarios",
						JOptionPane.ERROR_MESSAGE);
				}
		}
		
		public String getColumnName(int col) {
	        return columnNames[col];
	    }
	    
		@Override
		 public int getColumnCount() {
	        return columnNames.length;
	    }

		@Override
		public int getRowCount() {
	        return data.length;
	    }


		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int c) {
	         return getValueAt(0, c).getClass();
	    }
		public boolean isCellEditable(int row, int col) {
	        if(col==2)
	        	return true;
	        else
	        	return false;
	    }
		
		public void setValueAt(Object value, int row, int col) {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
		}
		
	}
	

}


package crud;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class JavaCRUD {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCRUD window = new JavaCRUD();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCRUD() {
		initialize();
		Connect();
		tableLoad();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javacrud","root","Pinku@123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void tableLoad() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {

		String bname = txtbname.getText();
		String edition = txtedition.getText();
		String price = txtprice.getText();
		
		try {
			pst=con.prepareStatement("insert into book(name,edition,price)value(?,?,?)");
			pst.setString(1,bname);
			pst.setString(2,edition);
			pst.setString(3,price);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Book Added...");
			tableLoad();
			txtbname.setText("");
			txtedition.setText("");
			txtprice.setText("");
			txtbname.requestFocus();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
	
	public void update() {
		String bname = txtbname.getText();
		String edition = txtedition.getText();
		String price = txtprice.getText();
		String bid = txtbid.getText();
		
		try {
			pst=con.prepareStatement("update book set name=?, edition=?, price=? where id=?");
			pst.setString(1,bname);
			pst.setString(2,edition);
			pst.setString(3,price);
			pst.setString(4, bid);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Book Updated...");
			tableLoad();
			txtbname.setText("");
			txtedition.setText("");
			txtprice.setText("");
			txtbname.requestFocus();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void delete() {
		 String bid = txtbid.getText();
			
			try {
				pst=con.prepareStatement("delete from book where id=?");
				pst.setString(1, bid);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Book Deleted...");
				tableLoad();
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	}
	
	public void getId() {
		 try {
             String id = txtbid.getText();
              pst = con.prepareStatement("select name,edition,price from book where id = ?");
              pst.setString(1, id);
              ResultSet rs = pst.executeQuery();

          if(rs.next()==true){
              String name = rs.getString(1);
              String edition = rs.getString(2);
              String price = rs.getString(3);
              
              txtbname.setText(name);
              txtedition.setText(edition);
              txtprice.setText(price);

          }else{
              txtbname.setText("");
              txtedition.setText("");
              txtprice.setText("");
               
          }
      }catch (Exception e2) {
         e2.printStackTrace();
      }
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 859, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(324, 10, 171, 55);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 255));
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(33, 91, 342, 182);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setForeground(new Color(255, 0, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 30, 89, 22);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setForeground(new Color(255, 0, 128));
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 123, 89, 22);
		panel.add(lblNewLabel_1_2);
		
		txtbname = new JTextField();
		txtbname.setBounds(162, 30, 153, 23);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(162, 123, 153, 23);
		panel.add(txtprice);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setForeground(new Color(255, 0, 128));
		lblNewLabel_1_1.setBounds(10, 79, 89, 22);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		txtedition = new JTextField();
		txtedition.setBounds(162, 79, 153, 23);
		panel.add(txtedition);
		txtedition.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setForeground(new Color(0, 0, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(156, 283, 101, 41);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(434, 90, 375, 234);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(33, 348, 342, 65);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1.setForeground(new Color(255, 0, 128));
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(26, 21, 89, 22);
		panel_1.add(lblNewLabel_1_1_1);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				getId();
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(143, 23, 153, 23);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(0, 0, 255));
		btnUpdate.setForeground(new Color(0, 0, 64));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(471, 344, 109, 55);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnClear_1_1 = new JButton("Delete");
		btnClear_1_1.setBackground(new Color(255, 0, 0));
		btnClear_1_1.setForeground(new Color(0, 0, 64));
		btnClear_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnClear_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear_1_1.setBounds(675, 344, 109, 55);
		frame.getContentPane().add(btnClear_1_1);
	}
}

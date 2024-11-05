package viewAdmin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAdmin frame = new AddAdmin();
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
	public AddAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter the admin's name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(72, 119, 188, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("You are adding now a new admin to the system");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(90, 25, 442, 52);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblEnterTheAdmins = new JLabel("Enter the admin's phone number:");
		lblEnterTheAdmins.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEnterTheAdmins.setBounds(72, 182, 255, 39);
		contentPane.add(lblEnterTheAdmins);
		
		JLabel lblEnterTheAdmins_1 = new JLabel("Enter the admin's password:");
		lblEnterTheAdmins_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEnterTheAdmins_1.setBounds(72, 247, 223, 39);
		contentPane.add(lblEnterTheAdmins_1);
		
		textField = new JTextField();
		textField.setBounds(370, 119, 180, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(370, 188, 180, 31);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(370, 259, 180, 31);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String adminName = textField.getText();
		        String phoneNumber = textField_1.getText();
		        String password = textField_2.getText();

		        DatabaseHelper dbHelper = new DatabaseHelper();

		        if (dbHelper.doesAdminExistByPhoneNumber(phoneNumber)) {
		            JOptionPane.showMessageDialog(null, "Admin with this phone number already exists!");
		        } else {
		            dbHelper.addAdmin(adminName, phoneNumber, password);
		            JOptionPane.showMessageDialog(null, "New admin added successfully!");
		        }
		    }
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(513, 335, 102, 39);
		contentPane.add(btnNewButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDashboard av = new AdminDashboard();
				av.setVisible(true);
				dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBounds(58, 335, 102, 39);
		contentPane.add(btnBack);
	}

}

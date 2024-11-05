package viewUser;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class StartView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartView frame = new StartView();
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
	public StartView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to our Travel Booking System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(114, 10, 536, 109);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Already a user? Welcome back!");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(41, 208, 243, 42);
		contentPane.add(lblNewLabel_1);
		
		JButton logibbtn = new JButton("Login");
		logibbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginP lp = new LoginP();
				lp.setVisible(true);
				dispose();
			}
		});
		logibbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		logibbtn.setBounds(114, 260, 117, 54);
		contentPane.add(logibbtn);
		
		JLabel lblNewLabel_2 = new JLabel("First time here? Join us!");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(402, 208, 207, 42);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Signup");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignupP sp = new SignupP();
				sp.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.setBounds(440, 260, 117, 54);
		contentPane.add(btnNewButton_1);
	}
}

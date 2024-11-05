package viewAdmin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashboard frame = new AdminDashboard();
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
	public AdminDashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add Admin");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAdmin aa = new AddAdmin();
				aa.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(259, 37, 116, 43);
		contentPane.add(btnNewButton);
		
		JButton btnManageHotels = new JButton("Manage Hotels");
		btnManageHotels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HotelMainView hv = new HotelMainView();
				hv.setVisible(true);
				dispose();
			}
		});
		btnManageHotels.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnManageHotels.setBounds(202, 192, 221, 43);
		contentPane.add(btnManageHotels);
		
		JButton btnManageFlights = new JButton("Manage Flights");
		btnManageFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlightMainView fv = new FlightMainView();
				fv.setVisible(true);
				dispose();
			}
		});
		btnManageFlights.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnManageFlights.setBounds(202, 108, 221, 43);
		contentPane.add(btnManageFlights);
		
		JButton btnNewButton_2_1 = new JButton("Manage Transportation");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TransporMainView tv = new TransporMainView();
				tv.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2_1.setBounds(202, 269, 221, 43);
		contentPane.add(btnNewButton_2_1);
	}

}

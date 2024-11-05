package viewUser;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String UserEmail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserDashboard frame = new UserDashboard();
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
    public UserDashboard(String userEmail) {
        this.UserEmail = userEmail;
        init();
    }

    public UserDashboard() {
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 622, 454);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnCreateNewBooking = new JButton("Create New Booking");
        btnCreateNewBooking.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		FlightSelectionView fs = new FlightSelectionView(UserEmail);
        		fs.setVisible(true);
        		dispose();
        	}
        });
        btnCreateNewBooking.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCreateNewBooking.setBounds(218, 50, 193, 57);
        contentPane.add(btnCreateNewBooking);

        JButton btnCurrentBookings = new JButton("My Current Bookings");
        btnCurrentBookings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentBookView cb = new CurrentBookView(UserEmail);
                cb.setVisible(true);
                dispose();
            }
        });
        btnCurrentBookings.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCurrentBookings.setBounds(218, 130, 193, 57);
        contentPane.add(btnCurrentBookings);

        JButton btnAccountSettings = new JButton("Account Settings");
        btnAccountSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditProfileView ep = new EditProfileView(UserEmail);
                ep.setVisible(true);
                dispose();
            }
        });
        btnAccountSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAccountSettings.setBounds(218, 210, 193, 57);
        contentPane.add(btnAccountSettings);

        JButton btnCancelBooking = new JButton("Cancel Booking");
        btnCancelBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CancelBookView cancelView = new CancelBookView(UserEmail);
                cancelView.setVisible(true);
                dispose();
            }
        });
        btnCancelBooking.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCancelBooking.setBounds(218, 290, 193, 57);
        contentPane.add(btnCancelBooking);
    }
}

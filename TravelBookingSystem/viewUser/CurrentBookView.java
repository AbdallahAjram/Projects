package viewUser;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Booking;
import model.DatabaseHelper;

public class CurrentBookView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String userEmail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CurrentBookView frame = new CurrentBookView("test@example.com"); // Change as needed
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
    public CurrentBookView(String userEmail) {
        this.userEmail = userEmail;
        init();
    }

    public CurrentBookView() {
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 572, 428);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("My Current Bookings");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(bookingPanel);
        scrollPane.setPreferredSize(new Dimension(550, 350));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        displayBookingInfo(bookingPanel);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.addActionListener(e -> {
            UserDashboard dashboard = new UserDashboard(userEmail);
            dashboard.setVisible(true);
            dispose();
        });
        contentPane.add(btnBack, BorderLayout.SOUTH);
    }

    private void displayBookingInfo(JPanel bookingPanel) {
        try {
            List<Booking> bookings = DatabaseHelper.getBookingsByEmail(userEmail);
            bookingPanel.removeAll(); 

            if (bookings != null && !bookings.isEmpty()) {
                for (Booking booking : bookings) {
                    if (Booking.Status.CONFIRMED.equals(booking.getStatus())) {
                        JPanel bookingEntry = new JPanel();
                        bookingEntry.setLayout(new BoxLayout(bookingEntry, BoxLayout.Y_AXIS));

                        JLabel lblFlight = new JLabel("Flight: " + booking.getFlight() + " to " + booking.getDestination());
                        bookingEntry.add(lblFlight);

                        JLabel lblHotel = new JLabel("Hotel: " + booking.getHotel());
                        bookingEntry.add(lblHotel);

                        JLabel lblTransport = new JLabel("Transport: " + booking.getTransportation());
                        bookingEntry.add(lblTransport);

                        JLabel lblStatus = new JLabel("Status: " + booking.getStatus());
                        bookingEntry.add(lblStatus);

                        bookingPanel.add(bookingEntry);
                        bookingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    }
                }
                if (bookingPanel.getComponentCount() == 0) {
                    JLabel lblNoBooking = new JLabel("You have no confirmed bookings.");
                    bookingPanel.add(lblNoBooking);
                }

            } else {
                JLabel lblNoBooking = new JLabel("You have no confirmed bookings.");
                bookingPanel.add(lblNoBooking);
            }

            bookingPanel.revalidate(); 
            bookingPanel.repaint();    

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving bookings.");
        }
    }
}

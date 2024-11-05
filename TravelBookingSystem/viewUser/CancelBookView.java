package viewUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

public class CancelBookView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String userEmail;
    private JComboBox<String> bookingComboBox;
    private ArrayList<String> bookingIds;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CancelBookView frame = new CancelBookView();
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
    public CancelBookView(String userEmail) {
        this.userEmail = userEmail;
        init();
    }

    public CancelBookView() {
        init();
    }

    public void init() {
        setTitle("Cancel Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(758, 392);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        bookingComboBox = new JComboBox<>();
        bookingComboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
        bookingComboBox.setBounds(196, 10, 359, 41);
        bookingComboBox.setPreferredSize(new Dimension(300, 30));
        bookingIds = new ArrayList<>();
        loadUserBookings();
        contentPane.setLayout(null);

        contentPane.add(bookingComboBox);

        JButton cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.setBounds(533, 257, 170, 56);
        cancelBookingButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        cancelBookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });
        contentPane.add(cancelBookingButton);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(26, 257, 178, 56);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserDashboard dashboard = new UserDashboard(userEmail);
                dashboard.setVisible(true);
                dispose();
            }
        });
        contentPane.add(backButton);
    }

    private void loadUserBookings() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseHelper.getConnection();
            String query = "SELECT booking_id, flight, destination FROM bookings WHERE user_email = ? AND status = 'CONFIRMED'";
            statement = connection.prepareStatement(query);
            statement.setString(1, userEmail);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookingId = resultSet.getString("booking_id");
                String flight = resultSet.getString("flight");
                String destination = resultSet.getString("destination");
                bookingComboBox.addItem(flight + " to " + destination);
                bookingIds.add(bookingId);
            }

            if (bookingComboBox.getItemCount() == 0) {
                bookingComboBox.addItem("No confirmed bookings");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection, statement, resultSet);
        }
    }

    private void cancelBooking() {
        if (bookingComboBox.getSelectedItem() == null || bookingComboBox.getSelectedItem().equals("No confirmed bookings")) {
            JOptionPane.showMessageDialog(this, "No booking selected to cancel.");
            return;
        }

        int selectedBookingIndex = bookingComboBox.getSelectedIndex();
        String selectedBookingId = bookingIds.get(selectedBookingIndex);

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseHelper.getConnection();
            String updateQuery = "UPDATE bookings SET status = 'CANCELED' WHERE booking_id = ?";
            statement = connection.prepareStatement(updateQuery);
            statement.setString(1, selectedBookingId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Booking canceled successfully.");
                bookingComboBox.removeItemAt(selectedBookingIndex);
                bookingIds.remove(selectedBookingIndex);

                if (bookingComboBox.getItemCount() == 0) {
                    bookingComboBox.addItem("No confirmed bookings");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel the booking.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection, statement, null);
        }
    }
}

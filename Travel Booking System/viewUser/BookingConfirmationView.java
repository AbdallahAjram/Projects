package viewUser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

public class BookingConfirmationView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String AirlineName;
    private String Destination;
    private String Hotel_name;
    private String Transport_type;
    private String UserEmail;
    private String DepartDate;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookingConfirmationView frame = new BookingConfirmationView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BookingConfirmationView() {
        init();
    }

    public BookingConfirmationView(String airlineName, String destination, String hotel_name, String transport_type,
            String userEmail, String departDate) {
        AirlineName = airlineName;
        Destination = destination;
        Hotel_name = hotel_name;
        Transport_type = transport_type;
        UserEmail = userEmail;
        DepartDate = departDate;
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 611, 407);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel(new GridLayout(5, 1));
        JLabel flightLabel = new JLabel("Flight: " + AirlineName);
        flightLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel destinationLabel = new JLabel("Destination: " + Destination);
        destinationLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel hotelLabel = new JLabel("Hotel: " + Hotel_name);
        hotelLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel transportLabel = new JLabel("Transport: " + Transport_type);
        transportLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel departDateLabel = new JLabel("Departure Date: " + DepartDate);
        departDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        topPanel.add(flightLabel);
        topPanel.add(destinationLabel);
        topPanel.add(hotelLabel);
        topPanel.add(transportLabel);
        topPanel.add(departDateLabel);
        contentPane.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        JButton confirmButton = new JButton("Confirm Booking");
        bottomPanel.add(confirmButton);
        confirmButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (addBookingToDatabase()) {
                    JOptionPane.showMessageDialog(null, "Booking Confirmed!");
                 
                    UserDashboard dashboard = new UserDashboard(UserEmail);
                    dashboard.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to confirm booking. Please try again.");
                }
            }
        });
    }

    private boolean addBookingToDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseHelper.getConnection();
            String query = "INSERT INTO bookings (user_email, destination, flight, depart_date, hotel, transportation) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, UserEmail);
            statement.setString(2, Destination);
            statement.setString(3, AirlineName);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date utilDate = sdf.parse(DepartDate);
            Date sqlDate = new Date(utilDate.getTime());

            statement.setDate(4, sqlDate);
            statement.setString(5, Hotel_name);
            statement.setString(6, Transport_type);
            

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseHelper.closeConnection(connection, statement, null);
        }
    }
}

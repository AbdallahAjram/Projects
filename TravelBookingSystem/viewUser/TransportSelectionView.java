package viewUser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

public class TransportSelectionView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String AirlineName;
    private String Destination;
    private String Hotel_name;
    private String Transport_type;
    private String UserEmail;
    private JComboBox<String> transportComboBox;
    private String DepartDate;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TransportSelectionView frame = new TransportSelectionView();
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
    public TransportSelectionView(String airlineName, String destination, String hotel_name, String userEmail,String DepartDate) {
        this.AirlineName = airlineName;
        this.Destination = destination;
        this.Hotel_name = hotel_name;
        this.UserEmail = userEmail;
        this.DepartDate=DepartDate;
        
        init();
    }

    public TransportSelectionView() {
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 611, 407);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        JLabel flightLabel = new JLabel("Flight: " + AirlineName);
        flightLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel destinationLabel = new JLabel("Destination: " + Destination);
        destinationLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel hotelLabel = new JLabel("Hotel: " + Hotel_name);
        hotelLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        topPanel.add(flightLabel);
        topPanel.add(destinationLabel);
        topPanel.add(hotelLabel);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        transportComboBox = new JComboBox<>(getTransportByDestination(Destination));
        transportComboBox.setFont(new Font("Tahoma", Font.BOLD, 16));
        transportComboBox.setBounds(26, 10, 514, 47);
        centerPanel.add(transportComboBox);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for Next button
        JPanel bottomPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Transport_type = (String) transportComboBox.getSelectedItem();
                BookingConfirmationView bookingConfirmationView = new BookingConfirmationView(AirlineName, Destination, Hotel_name, Transport_type, UserEmail,DepartDate);
                bookingConfirmationView.setVisible(true);
                dispose();
            }
        });
        bottomPanel.add(nextButton);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }

    private String[] getTransportByDestination(String destination) {
        ArrayList<String> transports = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseHelper.getConnection();
            String query = "SELECT type FROM transportation WHERE location = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, destination);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                transports.add(resultSet.getString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection, statement, resultSet);
        }

        return transports.toArray(new String[0]);
    }
}

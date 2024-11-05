package viewUser;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DatabaseHelper;
import java.awt.Font;

public class FlightSelectionView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String UserEmail;
    private String AirlineName;
    private String Destination;
    private String DepartDate;
    private JComboBox<String> flightComboBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FlightSelectionView frame = new FlightSelectionView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FlightSelectionView(String userEmail) {
        UserEmail = userEmail;
        init();
    }

    public FlightSelectionView() {
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 537, 402);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectFlight = new JLabel("Select a Flight:");
        lblSelectFlight.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectFlight.setBounds(10, 11, 131, 27);
        contentPane.add(lblSelectFlight);

        flightComboBox = new JComboBox<>();
        flightComboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
        flightComboBox.setBounds(151, 8, 300, 36);
        contentPane.add(flightComboBox);

        loadFlightsFromDatabase();

        JButton btnNext = new JButton("Next");
        btnNext.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNext.setBounds(387, 297, 100, 36);
        contentPane.add(btnNext);

        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFlight = (String) flightComboBox.getSelectedItem();
                if (selectedFlight != null) {
                    String[] parts = selectedFlight.split(" - ");
                    AirlineName = parts[0];
                    Destination = parts[1];

                    DepartDate = getDepartureDate(AirlineName, Destination);

                    HotelSelectionView hotelSelectionView = new HotelSelectionView(UserEmail, AirlineName, Destination, DepartDate);
                    hotelSelectionView.setVisible(true);
                    dispose();
                }
            }
        });
    }

    private void loadFlightsFromDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseHelper.getConnection();
            String query = "SELECT airline_name, destination FROM flights";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            ArrayList<String> flights = new ArrayList<>();

            while (resultSet.next()) {
                String airlineName = resultSet.getString("airline_name");
                String destination = resultSet.getString("destination");
                flights.add(airlineName + " - " + destination);
            }

            for (String flight : flights) {
                flightComboBox.addItem(flight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection, statement, resultSet);
        }
    }

    private String getDepartureDate(String airlineName, String destination) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseHelper.getConnection();
            String query = "SELECT departure_date FROM flights WHERE airline_name = ? AND destination = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, airlineName);
            statement.setString(2, destination);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("departure_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection, statement, resultSet);
        }

        return null;
    }
}

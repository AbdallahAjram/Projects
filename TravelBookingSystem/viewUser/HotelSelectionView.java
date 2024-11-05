package viewUser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
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

public class HotelSelectionView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String AirlineName;
    private String Destination;
    private String Hotel_name;
    private String UserEmail;
    private JComboBox<String> hotelComboBox;
	private String DepartDate;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HotelSelectionView frame = new HotelSelectionView();
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
    public HotelSelectionView(String userEmail, String airlineName, String destination, String departDate) {
        UserEmail = userEmail;
        AirlineName = airlineName;
        Destination = destination;
        DepartDate = departDate;
        init();
    }

    public HotelSelectionView() {
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 611, 407);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JLabel flightLabel = new JLabel("Flight: " + AirlineName);
        flightLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        JLabel destinationLabel = new JLabel("Destination: " + Destination);
        destinationLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        topPanel.add(flightLabel);
        topPanel.add(destinationLabel);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        hotelComboBox = new JComboBox<>(getHotelsByDestination(Destination));
        hotelComboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
        hotelComboBox.setBounds(26, 10, 514, 47);
        centerPanel.add(hotelComboBox);

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        nextButton.setBounds(463, 258, 101, 47);
        centerPanel.add(nextButton);

        nextButton.addActionListener(e -> {
            Hotel_name = (String) hotelComboBox.getSelectedItem();
            TransportSelectionView transportView = new TransportSelectionView(AirlineName, Destination, Hotel_name, UserEmail, DepartDate);
            transportView.setVisible(true);
            dispose();
        });

        contentPane.add(centerPanel, BorderLayout.CENTER);
    }

    private String[] getHotelsByDestination(String destination) {
        ArrayList<String> hotels = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseHelper.getConnection();
            String query = "SELECT name FROM hotels WHERE location = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, destination);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                hotels.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection, statement, resultSet);
        }

        return hotels.toArray(new String[0]);
    }
}

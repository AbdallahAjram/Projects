package viewAdmin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;
import model.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditFlightView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldAirline;
    private JTextField textFieldDestination;
    private JTextField textFieldDepartureDate;
    private JTextField textFieldPrice;
    private JComboBox<String> comboBoxFlights;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditFlightView frame = new EditFlightView();
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
    public EditFlightView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectFlight = new JLabel("Select Flight:");
        lblSelectFlight.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectFlight.setBounds(124, 91, 150, 30);
        contentPane.add(lblSelectFlight);

        comboBoxFlights = new JComboBox<>();
        comboBoxFlights.setFont(new Font("Tahoma", Font.BOLD, 14));
        comboBoxFlights.setBounds(294, 91, 200, 30);
        contentPane.add(comboBoxFlights);
        comboBoxFlights.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                try {
					loadFlightDetails();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        JLabel lblAirline = new JLabel("Airline Name:");
        lblAirline.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblAirline.setBounds(124, 141, 150, 30);
        contentPane.add(lblAirline);

        textFieldAirline = new JTextField();
        textFieldAirline.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldAirline.setBounds(294, 141, 200, 30);
        contentPane.add(textFieldAirline);
        textFieldAirline.setColumns(10);

        JLabel lblDestination = new JLabel("Destination:");
        lblDestination.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDestination.setBounds(124, 181, 150, 30);
        contentPane.add(lblDestination);

        textFieldDestination = new JTextField();
        textFieldDestination.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldDestination.setBounds(294, 181, 200, 30);
        contentPane.add(textFieldDestination);
        textFieldDestination.setColumns(10);

        JLabel lblDepartureDate = new JLabel("Departure Date:");
        lblDepartureDate.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDepartureDate.setBounds(124, 221, 150, 30);
        contentPane.add(lblDepartureDate);

        textFieldDepartureDate = new JTextField();
        textFieldDepartureDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldDepartureDate.setBounds(294, 221, 200, 30);
        contentPane.add(textFieldDepartureDate);
        textFieldDepartureDate.setColumns(10);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrice.setBounds(124, 261, 150, 30);
        contentPane.add(lblPrice);

        textFieldPrice = new JTextField();
        textFieldPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPrice.setBounds(294, 261, 200, 30);
        contentPane.add(textFieldPrice);
        textFieldPrice.setColumns(10);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.setBounds(416, 347, 105, 30);
        contentPane.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFlight();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(44, 347, 105, 30);
        contentPane.add(btnBack);
        
        JLabel lblNewLabel = new JLabel("Edit Flight");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(31, 0, 150, 50);
        contentPane.add(lblNewLabel);
        
        JLabel lblDateFormat = new JLabel("\"yyyy-MM-dd\"");
        lblDateFormat.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDateFormat.setBounds(504, 208, 111, 54);
        contentPane.add(lblDateFormat);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FlightMainView frame = new FlightMainView();
                frame.setVisible(true);
                dispose();
            }
        });
        loadFlightOptions();
    }

    private void loadFlightOptions() {
        comboBoxFlights.removeAllItems();
        String query = "SELECT flight_id, airline_name FROM flights";
        try (ResultSet rs = DatabaseHelper.getConnection().createStatement().executeQuery(query)) {
            while (rs.next()) {
                String flightId = rs.getString("flight_id");
                String airlineName = rs.getString("airline_name");
                comboBoxFlights.addItem(flightId + " - " + airlineName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFlightDetails() throws SQLException {
        String selectedItem = (String) comboBoxFlights.getSelectedItem();
        if (selectedItem != null) {
            String flightId = selectedItem.split(" - ")[0];
            Flight flight = DatabaseHelper.getFlightDetails(flightId);
			if (flight != null) {
			    textFieldAirline.setText(flight.getAirline_name());
			    textFieldDestination.setText(flight.getDestination());
			    textFieldDepartureDate.setText(flight.getDeparture_date().toString());
			    textFieldPrice.setText(String.valueOf(flight.getPrice()));
			}
        }
    }

    private void updateFlight() {
        String selectedItem = (String) comboBoxFlights.getSelectedItem();
        if (selectedItem != null) {
            String flightId = selectedItem.split(" - ")[0];
            String airlineName = textFieldAirline.getText();
            String destination = textFieldDestination.getText();
            String departureDate = textFieldDepartureDate.getText();
            String priceString = textFieldPrice.getText();
            double price = Double.parseDouble(priceString);

            boolean success = DatabaseHelper.updateFlight(flightId, airlineName, destination, departureDate, price);
            if (success) {
                JOptionPane.showMessageDialog(this, "The flight was updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update the flight", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

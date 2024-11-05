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
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteFlightView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxFlights;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DeleteFlightView frame = new DeleteFlightView();
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
    public DeleteFlightView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 632, 436);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectFlight = new JLabel("Select Flight:");
        lblSelectFlight.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectFlight.setBounds(66, 116, 129, 30);
        contentPane.add(lblSelectFlight);

        comboBoxFlights = new JComboBox<>();
        comboBoxFlights.setBounds(230, 118, 300, 30);
        contentPane.add(comboBoxFlights);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setBounds(479, 284, 129, 63);
        contentPane.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete selected flight
                deleteFlight();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(30, 284, 129, 63);
        contentPane.add(btnBack);
        
        JLabel lblDeleteFlight = new JLabel("Delete Flight");
        lblDeleteFlight.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDeleteFlight.setBounds(30, 10, 150, 50);
        contentPane.add(lblDeleteFlight);
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
        try (ResultSet rs = DatabaseHelper.getFlightOptions()) {
            while (rs.next()) {
                String flightId = rs.getString("flight_id");
                String airlineName = rs.getString("airline_name");
                comboBoxFlights.addItem(flightId + " - " + airlineName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteFlight() {
        String selectedItem = (String) comboBoxFlights.getSelectedItem();
        if (selectedItem != null) {
            String flightId = selectedItem.split(" - ")[0];
            boolean success = DatabaseHelper.deleteFlight(flightId);
            if (success) {
                JOptionPane.showMessageDialog(this, "The flight was deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
          
                loadFlightOptions();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the flight", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

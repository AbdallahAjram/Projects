package viewAdmin;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.DatabaseHelper;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddFlView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField airlineNameField;
    private JTextField destinationField;
    private JTextField departureDateField;
    private JTextField priceField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddFlView frame = new AddFlView();
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
    public AddFlView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FlightMainView frame = new FlightMainView();
                frame.setVisible(true);
                dispose();
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(48, 338, 93, 39);
        contentPane.add(btnBack);

        JLabel lblAirlineName = new JLabel("Enter the airline name:");
        lblAirlineName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblAirlineName.setBounds(58, 61, 201, 44);
        contentPane.add(lblAirlineName);

        JLabel lblDestination = new JLabel("Enter the destination:");
        lblDestination.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDestination.setBounds(58, 115, 201, 44);
        contentPane.add(lblDestination);

        JLabel lblDepartureDate = new JLabel("Enter the departure date:");
        lblDepartureDate.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDepartureDate.setBounds(58, 172, 218, 44);
        contentPane.add(lblDepartureDate);

        JLabel lblDateFormat = new JLabel("\"yyyy-MM-dd\"");
        lblDateFormat.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDateFormat.setBounds(460, 174, 111, 54);
        contentPane.add(lblDateFormat);

        JLabel lblPrice = new JLabel("Enter the price:");
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrice.setBounds(58, 226, 201, 44);
        contentPane.add(lblPrice);

        airlineNameField = new JTextField();
        airlineNameField.setFont(new Font("Tahoma", Font.BOLD, 12));
        airlineNameField.setBounds(307, 74, 143, 31);
        contentPane.add(airlineNameField);
        airlineNameField.setColumns(10);

        destinationField = new JTextField();
        destinationField.setFont(new Font("Tahoma", Font.BOLD, 12));
        destinationField.setBounds(307, 130, 143, 31);
        contentPane.add(destinationField);
        destinationField.setColumns(10);

        departureDateField = new JTextField();
        departureDateField.setFont(new Font("Tahoma", Font.BOLD, 12));
        departureDateField.setBounds(307, 187, 143, 31);
        contentPane.add(departureDateField);
        departureDateField.setColumns(10);

        priceField = new JTextField();
        priceField.setFont(new Font("Tahoma", Font.BOLD, 12));
        priceField.setBounds(307, 241, 143, 31);
        contentPane.add(priceField);
        priceField.setColumns(10);

        JLabel lblAddFlight = new JLabel("Add a Flight");
        lblAddFlight.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblAddFlight.setBounds(236, 10, 143, 41);
        contentPane.add(lblAddFlight);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseHelper dh = new DatabaseHelper();
                try {
                    String airlineName = airlineNameField.getText();
                    String destination = destinationField.getText();
                    String departureDate = departureDateField.getText();
                    double price = Double.parseDouble(priceField.getText());

                    if (dh.addFlight(airlineName, destination, departureDate, price)) {
                        JOptionPane.showMessageDialog(null, "The flight is added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Couldn't add the flight", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAdd.setBounds(478, 338, 93, 39);
        contentPane.add(btnAdd);
    }
}

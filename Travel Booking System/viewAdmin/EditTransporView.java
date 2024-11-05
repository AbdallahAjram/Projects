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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditTransporView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldType;
    private JTextField textFieldLocation;
    private JTextField textFieldPrice;
    private JComboBox<String> comboBoxTransport;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditTransporView frame = new EditTransporView();
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
    public EditTransporView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectTransport = new JLabel("Select Transport:");
        lblSelectTransport.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectTransport.setBounds(115, 111, 150, 30);
        contentPane.add(lblSelectTransport);

        comboBoxTransport = new JComboBox<>();
        comboBoxTransport.setFont(new Font("Tahoma", Font.BOLD, 12));
        comboBoxTransport.setBounds(285, 111, 200, 30);
        contentPane.add(comboBoxTransport);
        comboBoxTransport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTransportDetails();
            }
        });

        JLabel lblType = new JLabel("Type:");
        lblType.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblType.setBounds(115, 161, 150, 30);
        contentPane.add(lblType);

        textFieldType = new JTextField();
        textFieldType.setFont(new Font("Tahoma", Font.BOLD, 12));
        textFieldType.setBounds(285, 161, 200, 30);
        contentPane.add(textFieldType);
        textFieldType.setColumns(10);

        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblLocation.setBounds(115, 201, 150, 30);
        contentPane.add(lblLocation);

        textFieldLocation = new JTextField();
        textFieldLocation.setFont(new Font("Tahoma", Font.BOLD, 12));
        textFieldLocation.setBounds(285, 201, 200, 30);
        contentPane.add(textFieldLocation);
        textFieldLocation.setColumns(10);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrice.setBounds(115, 241, 150, 30);
        contentPane.add(lblPrice);

        textFieldPrice = new JTextField();
        textFieldPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
        textFieldPrice.setBounds(285, 241, 200, 30);
        contentPane.add(textFieldPrice);
        textFieldPrice.setColumns(10);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.setBounds(445, 330, 123, 45);
        contentPane.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
                updateTransport();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(32, 330, 123, 45);
        contentPane.add(btnBack);
        
        JLabel lblEditTransportsDetails = new JLabel("Edit Transport's Details");
        lblEditTransportsDetails.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblEditTransportsDetails.setBounds(32, 0, 305, 63);
        contentPane.add(lblEditTransportsDetails);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TransporMainView frame = new TransporMainView();
                frame.setVisible(true);
                dispose();
            }
        });

        loadTransportOptions();
    }

    private void loadTransportOptions() {
        String query = "SELECT transport_id, type FROM transportation";
        try (PreparedStatement stmt = DatabaseHelper.getConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String transportId = rs.getString("transport_id");
                String type = rs.getString("type");
                comboBoxTransport.addItem(transportId + " - " + type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTransportDetails() {
        String selectedItem = (String) comboBoxTransport.getSelectedItem();
        if (selectedItem != null) {
            String transportId = selectedItem.split(" - ")[0];
            String query = "SELECT * FROM transportation WHERE transport_id = ?";
            try (PreparedStatement stmt = DatabaseHelper.getConnection().prepareStatement(query)) {
                stmt.setString(1, transportId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        textFieldType.setText(rs.getString("type"));
                        textFieldLocation.setText(rs.getString("location"));
                        textFieldPrice.setText(rs.getString("price"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTransport() {
        String selectedItem = (String) comboBoxTransport.getSelectedItem();
        if (selectedItem != null) {
            String transportId = selectedItem.split(" - ")[0];
            String type = textFieldType.getText();
            String location = textFieldLocation.getText();
            String priceString = textFieldPrice.getText();
            double price = Double.parseDouble(priceString);

            String query = "UPDATE transportation SET type = ?, location = ?, price = ? WHERE transport_id = ?";
            try (PreparedStatement stmt = DatabaseHelper.getConnection().prepareStatement(query)) {
                stmt.setString(1, type);
                stmt.setString(2, location);
                stmt.setDouble(3, price);
                stmt.setString(4, transportId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "The transportation option was updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update the transportation option", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

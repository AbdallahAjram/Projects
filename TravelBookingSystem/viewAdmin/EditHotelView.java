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

public class EditHotelView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldName;
    private JTextField textFieldLocation;
    private JTextField textFieldPricePerNight;
    private JComboBox<String> comboBoxHotels;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditHotelView frame = new EditHotelView();
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
    public EditHotelView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectHotel = new JLabel("Select Hotel:");
        lblSelectHotel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectHotel.setBounds(112, 109, 150, 30);
        contentPane.add(lblSelectHotel);

        comboBoxHotels = new JComboBox<>();
        comboBoxHotels.setFont(new Font("Tahoma", Font.BOLD, 14));
        comboBoxHotels.setBounds(282, 109, 200, 30);
        contentPane.add(comboBoxHotels);
        comboBoxHotels.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                loadHotelDetails();
            }
        });

        JLabel lblName = new JLabel("Hotel Name:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblName.setBounds(112, 159, 150, 30);
        contentPane.add(lblName);

        textFieldName = new JTextField();
        textFieldName.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldName.setBounds(282, 159, 200, 30);
        contentPane.add(textFieldName);
        textFieldName.setColumns(10);

        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblLocation.setBounds(112, 199, 150, 30);
        contentPane.add(lblLocation);

        textFieldLocation = new JTextField();
        textFieldLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldLocation.setBounds(282, 199, 200, 30);
        contentPane.add(textFieldLocation);
        textFieldLocation.setColumns(10);

        JLabel lblPricePerNight = new JLabel("Price Per Night:");
        lblPricePerNight.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPricePerNight.setBounds(112, 239, 150, 30);
        contentPane.add(lblPricePerNight);

        textFieldPricePerNight = new JTextField();
        textFieldPricePerNight.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPricePerNight.setBounds(282, 239, 200, 30);
        contentPane.add(textFieldPricePerNight);
        textFieldPricePerNight.setColumns(10);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.setBounds(464, 356, 100, 30);
        contentPane.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
                updateHotel();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(30, 356, 100, 30);
        contentPane.add(btnBack);
        
        JLabel lblNewLabel = new JLabel("Edit Hotel's Details");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(30, 0, 210, 63);
        contentPane.add(lblNewLabel);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HotelMainView frame = new HotelMainView();
                frame.setVisible(true);
                dispose();
            }
        });

        populateHotelComboBox();
    }

    private void populateHotelComboBox() {
        String query = "SELECT hotel_id, name FROM hotels";
        try (PreparedStatement stmt = DatabaseHelper.getConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String hotelId = rs.getString("hotel_id");
                String hotelName = rs.getString("name");
                comboBoxHotels.addItem(hotelId + " - " + hotelName); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadHotelDetails() {
        String selectedItem = (String) comboBoxHotels.getSelectedItem();
        if (selectedItem != null) {
            String hotelId = selectedItem.split(" - ")[0]; 
            String query = "SELECT * FROM hotels WHERE hotel_id = ?";
            try (PreparedStatement stmt = DatabaseHelper.getConnection().prepareStatement(query)) {
                stmt.setString(1, hotelId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        textFieldName.setText(rs.getString("name"));
                        textFieldLocation.setText(rs.getString("location"));
                        textFieldPricePerNight.setText(rs.getString("price_per_night"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateHotel() {
        String selectedItem = (String) comboBoxHotels.getSelectedItem();
        if (selectedItem != null) {
            String hotelId = selectedItem.split(" - ")[0];
            String name = textFieldName.getText();
            String location = textFieldLocation.getText();
            String pricePerNightString = textFieldPricePerNight.getText();
            double pricePerNight = Double.parseDouble(pricePerNightString); 

            String query = "UPDATE hotels SET name = ?, location = ?, price_per_night = ? WHERE hotel_id = ?";
            try (PreparedStatement stmt = DatabaseHelper.getConnection().prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, location);
                stmt.setDouble(3, pricePerNight);
                stmt.setString(4, hotelId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "The hotel was updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update the hotel", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

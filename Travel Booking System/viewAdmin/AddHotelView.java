package viewAdmin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

public class AddHotelView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldHotelName;
    private JTextField textFieldLocation;
    private JTextField textFieldPricePerNight;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddHotelView frame = new AddHotelView();
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
    public AddHotelView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 629, 472);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblHotelName = new JLabel("Hotel Name:");
        lblHotelName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHotelName.setBounds(102, 106, 150, 30);
        contentPane.add(lblHotelName);

        textFieldHotelName = new JTextField();
        textFieldHotelName.setFont(new Font("Tahoma", Font.BOLD, 12));
        textFieldHotelName.setBounds(272, 106, 200, 30);
        contentPane.add(textFieldHotelName);
        textFieldHotelName.setColumns(10);

        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblLocation.setBounds(102, 156, 150, 30);
        contentPane.add(lblLocation);

        textFieldLocation = new JTextField();
        textFieldLocation.setFont(new Font("Tahoma", Font.BOLD, 12));
        textFieldLocation.setBounds(272, 156, 200, 30);
        contentPane.add(textFieldLocation);
        textFieldLocation.setColumns(10);

        JLabel lblPricePerNight = new JLabel("Price Per Night:");
        lblPricePerNight.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPricePerNight.setBounds(102, 206, 150, 30);
        contentPane.add(lblPricePerNight);

        textFieldPricePerNight = new JTextField();
        textFieldPricePerNight.setFont(new Font("Tahoma", Font.BOLD, 12));
        textFieldPricePerNight.setBounds(272, 206, 200, 30);
        contentPane.add(textFieldPricePerNight);
        textFieldPricePerNight.setColumns(10);

        JButton btnAddHotel = new JButton("Add Hotel");
        btnAddHotel.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAddHotel.setBounds(417, 328, 130, 51);
        contentPane.add(btnAddHotel);
        btnAddHotel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
                addHotel();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBounds(39, 327, 115, 51);
        contentPane.add(btnBack);
        
        JLabel lblNewLabel = new JLabel("Add Hotel");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(39, 20, 136, 51);
        contentPane.add(lblNewLabel);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HotelMainView frame = new HotelMainView();
                frame.setVisible(true);
                dispose();
            }
        });
    }

    private void addHotel() {
        String hotelName = textFieldHotelName.getText();
        String location = textFieldLocation.getText();
        String pricePerNightString = textFieldPricePerNight.getText();

        if (hotelName.isEmpty() || location.isEmpty() || pricePerNightString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double pricePerNight;
        try {
            pricePerNight = Double.parseDouble(pricePerNightString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DatabaseHelper.addHotel(hotelName, location, pricePerNight);
        if (success) {
            JOptionPane.showMessageDialog(this, "Hotel added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            textFieldHotelName.setText("");
            textFieldLocation.setText("");
            textFieldPricePerNight.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add hotel", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

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
import java.sql.SQLException;

public class DeleteHotelView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxHotels;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DeleteHotelView frame = new DeleteHotelView();
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
    public DeleteHotelView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 533, 369);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectHotel = new JLabel("Select Hotel to Delete:");
        lblSelectHotel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectHotel.setBounds(73, 95, 250, 30);
        contentPane.add(lblSelectHotel);

        comboBoxHotels = new JComboBox<>();
        comboBoxHotels.setFont(new Font("Tahoma", Font.BOLD, 14));
        comboBoxHotels.setBounds(73, 145, 272, 43);
        contentPane.add(comboBoxHotels);
       
        loadHotelOptions();

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setBounds(359, 251, 100, 30);
        contentPane.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                deleteHotel();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(34, 251, 100, 30);
        contentPane.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HotelMainView frame = new HotelMainView();
                frame.setVisible(true);
                dispose();
            }
        });
    }

    private void loadHotelOptions() {
        try {
            DatabaseHelper.loadHotelOptions(comboBoxHotels);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load hotels.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteHotel() {
        String selectedItem = (String) comboBoxHotels.getSelectedItem();
        if (selectedItem != null) {
            String hotelId = selectedItem.split(" - ")[0];
            try {
                boolean success = DatabaseHelper.deleteHotel(hotelId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Hotel deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    comboBoxHotels.removeItem(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete the hotel", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while deleting the hotel.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hotel selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

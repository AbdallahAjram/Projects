package viewAdmin;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.DatabaseHelper; // Ensure this is the correct import
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class DeleteTransporView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxTransport;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DeleteTransporView frame = new DeleteTransporView();
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
    public DeleteTransporView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 607, 436);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDeleteTransport = new JLabel("Delete Transport");
        lblDeleteTransport.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDeleteTransport.setBounds(45, 10, 200, 30);
        contentPane.add(lblDeleteTransport);

        JLabel lblSelectTransport = new JLabel("Select Transport:");
        lblSelectTransport.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSelectTransport.setBounds(90, 124, 150, 30);
        contentPane.add(lblSelectTransport);

        comboBoxTransport = new JComboBox<>();
        comboBoxTransport.setFont(new Font("Tahoma", Font.BOLD, 12));
        comboBoxTransport.setBounds(250, 119, 218, 42);
        contentPane.add(comboBoxTransport);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setBounds(434, 290, 110, 42);
        contentPane.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTransport();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(50, 290, 110, 42);
        contentPane.add(btnBack);
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
        try {
            DatabaseHelper dh = new DatabaseHelper();
            comboBoxTransport.setModel(dh.getTransportOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTransport() {
        String selectedItem = (String) comboBoxTransport.getSelectedItem();
        if (selectedItem != null) {
            String transportId = selectedItem.split(" - ")[0];
            DatabaseHelper dh = new DatabaseHelper();
            if (dh.deleteTransportById(transportId)) {
                JOptionPane.showMessageDialog(this, "The transportation option was deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                comboBoxTransport.removeItem(selectedItem);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the transportation option", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

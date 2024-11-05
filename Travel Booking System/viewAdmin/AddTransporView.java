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

public class AddTransporView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField typeField;
    private JTextField locationField;
    private JTextField priceField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddTransporView frame = new AddTransporView();
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
    public AddTransporView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TransporMainView frame = new TransporMainView();
                frame.setVisible(true);
                dispose();
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(48, 338, 93, 39);
        contentPane.add(btnBack);
        
        JLabel lblType = new JLabel("Enter the type:");
        lblType.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblType.setBounds(121, 103, 201, 44);
        contentPane.add(lblType);
        
        JLabel lblLocation = new JLabel("Enter the location:");
        lblLocation.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblLocation.setBounds(121, 157, 201, 44);
        contentPane.add(lblLocation);
        
        JLabel lblPrice = new JLabel("Enter the price:");
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrice.setBounds(121, 214, 201, 44);
        contentPane.add(lblPrice);
        
        typeField = new JTextField();
        typeField.setFont(new Font("Tahoma", Font.BOLD, 12));
        typeField.setBounds(370, 116, 143, 31);
        contentPane.add(typeField);
        typeField.setColumns(10);
        
        locationField = new JTextField();
        locationField.setFont(new Font("Tahoma", Font.BOLD, 12));
        locationField.setBounds(370, 172, 143, 31);
        contentPane.add(locationField);
        locationField.setColumns(10);
        
        priceField = new JTextField();
        priceField.setFont(new Font("Tahoma", Font.BOLD, 12));
        priceField.setBounds(370, 229, 143, 31);
        contentPane.add(priceField);
        priceField.setColumns(10);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseHelper dh = new DatabaseHelper();
                if(dh.addTransportation(typeField.getText(), locationField.getText(), Double.parseDouble(priceField.getText()))) {
                    JOptionPane.showMessageDialog(null, "The transportation option is added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't add the transportation option", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAdd.setBounds(478, 338, 93, 39);
        contentPane.add(btnAdd);
        
        JLabel lblNewLabel = new JLabel("Add Transportation");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(48, 10, 234, 49);
        contentPane.add(lblNewLabel);
    }
}

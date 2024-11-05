package viewAdmin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DatabaseHelper;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class AdminLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField phoneNumberField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminLogin frame = new AdminLogin();
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
    public AdminLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 713, 459);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Admin Login Page");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(29, 0, 173, 57);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Enter your phone number:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1.setBounds(86, 90, 227, 28);
        contentPane.add(lblNewLabel_1);
        
        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(366, 94, 126, 26);
        contentPane.add(phoneNumberField);
        phoneNumberField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Enter your password:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_2.setBounds(86, 183, 214, 38);
        contentPane.add(lblNewLabel_2);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(366, 192, 126, 26);
        contentPane.add(passwordField);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                String password = new String(passwordField.getPassword());
                AdminDashboard ad = new AdminDashboard();
                ad.setVisible(true);
                dispose();
                try {
                    if (DatabaseHelper.authenticateAdmin(phoneNumber, password)) {
                        
                    } else {
                        JOptionPane.showMessageDialog(AdminLogin.this, "Invalid phone number or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AdminLogin.this, "An error occurred during login", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLogin.setBounds(519, 326, 85, 38);
        contentPane.add(btnLogin);
    }
    
}

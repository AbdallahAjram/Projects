package viewUser;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import model.DatabaseHelper;
import model.Users;

import java.sql.SQLException;

public class EditProfileView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private String currentUserEmail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditProfileView frame = new EditProfileView();
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
    public EditProfileView(){
        init();
    }
    
    public EditProfileView(String userEmail) {
        this.currentUserEmail = userEmail;
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 554, 489);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Edit Your Profile");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(30, 10, 208, 57);
        contentPane.add(lblTitle);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(124, 100, 100, 30);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setFont(new Font("Tahoma", Font.BOLD, 12));
        nameField.setBounds(234, 100, 200, 30);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEmail.setBounds(124, 150, 100, 30);
        contentPane.add(lblEmail);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.BOLD, 12));
        emailField.setBounds(234, 150, 200, 30);
        contentPane.add(emailField);
        emailField.setColumns(10);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPhone.setBounds(124, 200, 100, 30);
        contentPane.add(lblPhone);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Tahoma", Font.BOLD, 12));
        phoneField.setBounds(234, 200, 200, 30);
        contentPane.add(phoneField);
        phoneField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(124, 250, 100, 30);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.BOLD, 12));
        passwordField.setBounds(234, 250, 200, 30);
        contentPane.add(passwordField);

        loadUserInfo(currentUserEmail);

        JButton btnSave = new JButton("Save Changes");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    boolean success = DatabaseHelper.updateUserInfo(currentUserEmail, name, email, phone, password);
                    if (success) {
                        currentUserEmail = email;
                        showMessage("Profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        showMessage("Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showMessage("An error occurred while updating the profile.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSave.setBounds(354, 368, 150, 40);
        contentPane.add(btnSave);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserDashboard ud = new UserDashboard(currentUserEmail);
                ud.setVisible(true);
                dispose();
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBounds(41, 370, 150, 40);
        contentPane.add(btnBack);
    }

    private void loadUserInfo(String userEmail) {
        try {
            Users user = DatabaseHelper.getUserByEmail(userEmail);
            if (user != null) {
                nameField.setText(user.getName());       
                passwordField.setText(user.getPassword());
                emailField.setText(user.getEmail());       
                phoneField.setText(user.getPhone());     
            } else {
                showMessage("User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("An error occurred while loading user information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}

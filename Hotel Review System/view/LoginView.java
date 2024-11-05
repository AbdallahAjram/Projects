package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import model.DatabaseHelper;
import model.DatabaseHelper.AuthenticationResult;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField NumberF;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JLabel lblNewLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginView frame = new LoginView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 799, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Phone Number:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(133, 71, 130, 45);
        contentPane.add(lblNewLabel_1);

        NumberF = new JTextField();
        NumberF.setBounds(273, 80, 164, 31);
        contentPane.add(NumberF);
        NumberF.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Password:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setBounds(133, 138, 83, 45);
        contentPane.add(lblNewLabel_2);

        btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = getNumber();
                String password = getPassword();
                AuthenticationResult result = DatabaseHelper.authenticateCustomer(phoneNumber, password);
                switch (result) {
                    case SUCCESS:
                        CustomerView cv = new CustomerView(phoneNumber);
                        cv.setVisible(true);
                        dispose();
                        break;
                    case WRONG_PASSWORD:
                        JOptionPane.showMessageDialog(
                            contentPane,
                            "Wrong password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        break;
                    case NO_SUCH_USER:
                        JOptionPane.showMessageDialog(
                            contentPane,
                            "There is no account with this number, please sign up.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        break;
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setBounds(489, 221, 106, 45);
        contentPane.add(btnNewButton);

        JLabel lblNewLabel_3 = new JLabel("Login Page");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_3.setBounds(40, 10, 136, 51);
        contentPane.add(lblNewLabel_3);

        passwordField = new JPasswordField();
        passwordField.setBounds(226, 153, 130, 19);
        contentPane.add(passwordField);
        
        btnNewButton_1 = new JButton("Signup");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignupView sv = new SignupView();
                sv.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_1.setBounds(107, 320, 88, 45);
        contentPane.add(btnNewButton_1);
        
        lblNewLabel = new JLabel("Don't have an account?");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(75, 276, 164, 34);
        contentPane.add(lblNewLabel);
    }

    public String getNumber() {
        return NumberF.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginButtonListener(ActionListener listener) {
        btnNewButton.addActionListener(listener);
    }
}

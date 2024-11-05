package viewUser;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.DatabaseHelper; // Import the DatabaseHelper class
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignupP extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameFl;
    private JTextField emailFl;
    private JTextField phoneFl;
    private JPasswordField pass1Fl;
    private JPasswordField pass2Fl;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignupP frame = new SignupP();
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
    public SignupP() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 467);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Signup Page");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(25, 10, 116, 39);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Enter your name:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(96, 59, 126, 39);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Enter your email:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setBounds(96, 108, 126, 39);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Enter your phone number:");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_3.setBounds(96, 162, 200, 39);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Enter your password:");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_4.setBounds(96, 221, 159, 27);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Confirm your password:");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_5.setBounds(96, 275, 173, 20);
        contentPane.add(lblNewLabel_5);

        nameFl = new JTextField();
        nameFl.setBounds(313, 67, 199, 28);
        contentPane.add(nameFl);
        nameFl.setColumns(10);

        emailFl = new JTextField();
        emailFl.setColumns(10);
        emailFl.setBounds(313, 120, 199, 28);
        contentPane.add(emailFl);

        phoneFl = new JTextField();
        phoneFl.setColumns(10);
        phoneFl.setBounds(313, 170, 199, 28);
        contentPane.add(phoneFl);

        pass1Fl = new JPasswordField();
        pass1Fl.setBounds(313, 223, 199, 28);
        contentPane.add(pass1Fl);

        pass2Fl = new JPasswordField();
        pass2Fl.setBounds(313, 274, 199, 28);
        contentPane.add(pass2Fl);

        JButton btnNewButton = new JButton("Signup");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameFl.getText();
                String email = emailFl.getText();
                String phone = phoneFl.getText();
                String password = new String(pass1Fl.getPassword());
                String confirmPassword = new String(pass2Fl.getPassword());

                DatabaseHelper dh = new DatabaseHelper();
                
                if (dh.userExists(email)) {
                    JOptionPane.showMessageDialog(null, "Email already exists. Please login.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean userAdded = dh.addUser(name, email, phone, password);
                        if (userAdded) {
                            JOptionPane.showMessageDialog(null, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            LoginP lp = new LoginP();  
                            lp.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Signup failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(468, 352, 117, 44);
        contentPane.add(btnNewButton);

        JLabel lblNewLabel_6 = new JLabel("Already have an account?");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_6.setBounds(60, 338, 173, 27);
        contentPane.add(lblNewLabel_6);

        JButton btnNewButton_1 = new JButton("Login");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginP lp = new LoginP();
                lp.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton_1.setBounds(96, 375, 92, 28);
        contentPane.add(btnNewButton_1);
    }
}

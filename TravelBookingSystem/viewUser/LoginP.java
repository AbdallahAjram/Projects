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

public class LoginP extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Einput;
    private JPasswordField Pinput;
    private String UserEmail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginP frame = new LoginP();
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
    public LoginP() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 733, 406);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Enter your email:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(103, 94, 148, 29);
        contentPane.add(lblNewLabel);

        Einput = new JTextField();
        Einput.setBounds(292, 97, 172, 28);
        contentPane.add(Einput);
        Einput.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Enter your password:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1.setBounds(103, 171, 181, 29);
        contentPane.add(lblNewLabel_1);

        Pinput = new JPasswordField();
        Pinput.setBounds(292, 171, 172, 28);
        contentPane.add(Pinput);

        JButton Lbtn = new JButton("Login");
        Lbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = Einput.getText();
                String password = new String(Pinput.getPassword());

                DatabaseHelper dh = new DatabaseHelper();
                
                
                if (!dh.userExists(email)) {
                    JOptionPane.showMessageDialog(null, "Email not found. Please sign up.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                   
                    if (dh.authenticateUser(email, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        UserEmail=email;
                        UserDashboard UD = new UserDashboard(UserEmail);
                        UD.setVisible(true);
                        dispose();
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        Lbtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        Lbtn.setBounds(470, 262, 117, 44);
        contentPane.add(Lbtn);

        JLabel lblNewLabel_2 = new JLabel("Not a user?");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2.setBounds(58, 271, 91, 29);
        contentPane.add(lblNewLabel_2);

        JButton btnNewButton_1 = new JButton("Signup");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignupP sp = new SignupP();
                sp.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton_1.setBounds(58, 298, 91, 29);
        contentPane.add(btnNewButton_1);

        JLabel lblNewLabel_3 = new JLabel("Login Page");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_3.setBounds(51, 10, 98, 52);
        contentPane.add(lblNewLabel_3);
    }
}

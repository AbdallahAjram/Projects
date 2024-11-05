package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Customer;
import model.DatabaseHelper;

public class SignupView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField sNamef;
    private JTextField sNumf;
    private JPasswordField sPassf;
    private JButton signupButton;
    private JLabel lblNewLabel_3;
    private JButton btnNewButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignupView frame = new SignupView();
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
    public SignupView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 729, 438);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Enter your Name:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(86, 36, 128, 25);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Enter your phone number:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(86, 105, 201, 25);
        contentPane.add(lblNewLabel_1);

        sNamef = new JTextField();
        sNamef.setBounds(273, 41, 120, 19);
        contentPane.add(sNamef);
        sNamef.setColumns(10);

        sNumf = new JTextField();
        sNumf.setBounds(297, 110, 96, 19);
        contentPane.add(sNumf);
        sNumf.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Enter your password:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_2.setBounds(86, 170, 170, 25);
        contentPane.add(lblNewLabel_2);

        signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = getNumber();
                String name = getName();
                String password = getPassword();
                
                if (DatabaseHelper.isCustomerRegistered(phoneNumber)) {
                    JOptionPane.showMessageDialog(
                        contentPane,
                        "An account with this phone number already exists. Please log in.",
                        "Signup Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    Customer c = new Customer(phoneNumber, name, password);
                    DatabaseHelper.insertCustomer(c);
                    CustomerView cv = new CustomerView(phoneNumber);
                    cv.setVisible(true);
                    dispose();
                }
            }
        });

        signupButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        signupButton.setBounds(473, 227, 128, 29);
        contentPane.add(signupButton);

        sPassf = new JPasswordField();
        sPassf.setBounds(297, 175, 96, 19);
        contentPane.add(sPassf);
        
        lblNewLabel_3 = new JLabel("Already have an account?");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_3.setBounds(75, 278, 170, 36);
        contentPane.add(lblNewLabel_3);
        
        btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginView lv = new LoginView();
        		lv.setVisible(true);
        		dispose();
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(86, 311, 128, 29);
        contentPane.add(btnNewButton);
    }

    public String getNumber() {
        return sNumf.getText();
    }

    public String getName() {
        return sNamef.getText();
    }

    public String getPassword() {
        return new String(sPassf.getPassword());
    }

    public void addSignupButtonListener(ActionListener listener) {
        signupButton.addActionListener(listener);
    }
}

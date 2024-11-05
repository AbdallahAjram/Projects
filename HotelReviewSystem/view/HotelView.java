package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.DatabaseHelper;
import model.Hotel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HotelView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Hname;
    private JTextField Hloc;
    private String userPhoneNumber;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HotelView frame = new HotelView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public HotelView() {
        initial();
    }

    public HotelView(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        initial();
    }

    public void initial() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 634, 419);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Enter the hotel name:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(89, 95, 164, 26);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Enter the hotel location:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(89, 180, 175, 26);
        contentPane.add(lblNewLabel_1);

        Hname = new JTextField();
        Hname.setBounds(277, 98, 175, 25);
        contentPane.add(Hname);
        Hname.setColumns(10);

        Hloc = new JTextField();
        Hloc.setColumns(10);
        Hloc.setBounds(274, 183, 175, 25);
        contentPane.add(Hloc);

        JButton btnAdd = new JButton("ADD");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Hotel h = new Hotel(getHName(), getHLoc());
                boolean isInserted = DatabaseHelper.insertHotel(h);
                if (isInserted) {
                    CustomerView cv = new CustomerView(userPhoneNumber);
                    cv.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(
                        contentPane,
                        "A hotel with this name already exists",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(454, 285, 85, 32);
        contentPane.add(btnAdd);

        JLabel lblNewLabel_2 = new JLabel("Hotel's informations");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_2.setBounds(210, 22, 190, 26);
        contentPane.add(lblNewLabel_2);

        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerView cv = new CustomerView(userPhoneNumber);
                cv.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(89, 285, 85, 32);
        contentPane.add(btnNewButton);
    }

    private String getHName() {
        return Hname.getText();
    }

    private String getHLoc() {
        return Hloc.getText();
    }
}

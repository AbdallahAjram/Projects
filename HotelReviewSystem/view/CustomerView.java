package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.DatabaseHelper;

public class CustomerView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnViewHotels;
    private JButton btnAddHotel;
    private JButton btnRateHotel;
    private String userPhoneNumber;
    private JLabel lblWelcome;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerView frame = new CustomerView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CustomerView(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        initialize();
        updateWelcomeMessage();
    }

    public CustomerView() {
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 604, 397);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblWelcome = new JLabel("WELCOME");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWelcome.setBounds(240, 32, 200, 25);
        contentPane.add(lblWelcome);

        btnAddHotel = new JButton("Add Hotel");
        btnAddHotel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HotelView hv = new HotelView(userPhoneNumber);
                hv.setVisible(true);
                dispose();
            }
        });
        btnAddHotel.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAddHotel.setBounds(236, 170, 121, 45);
        contentPane.add(btnAddHotel);

        btnRateHotel = new JButton("Rate Hotel");
        btnRateHotel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RateView rv = new RateView(userPhoneNumber);
                rv.updateHotelList();
                rv.setVisible(true);
                dispose();
            }
        });
        btnRateHotel.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnRateHotel.setBounds(236, 253, 121, 45);
        contentPane.add(btnRateHotel);

        btnViewHotels = new JButton("View Hotels");
        btnViewHotels.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HotelsView hv = new HotelsView(userPhoneNumber);
                hv.setVisible(true);
            }
        });
        btnViewHotels.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnViewHotels.setBounds(236, 81, 121, 45);
        contentPane.add(btnViewHotels);
    }

    private void updateWelcomeMessage() {
        String customerName = DatabaseHelper.getCustomerName(userPhoneNumber);
        if (customerName != null) {
            lblWelcome.setText("WELCOME " + customerName.toUpperCase());
        }
    }

    public void addAddHotelListener(ActionListener listener) {
        btnAddHotel.addActionListener(listener);
    }

    public void addRateHotelListener(ActionListener listener) {
        btnRateHotel.addActionListener(listener);
    }

    public void addViewHotelsListener(ActionListener listener) {
        btnViewHotels.addActionListener(listener);
    }
}

package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import model.DatabaseHelper;
import model.Rate;

public class RateView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> hotelComboBox;
    private JComboBox<Integer> ratingComboBox;
    private JTextArea feedbackTextArea;
    private JButton submitButton;
    private JButton btnNewButton;
    private String userPhoneNumber;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RateView frame = new RateView();
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
    public RateView(String userPhoneNumber) {
    	setUserPhoneNumber(userPhoneNumber);;
    	initiall();
    }
    public RateView() {
    	initiall();
    }
    public void initiall() {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 602, 405);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Choose the hotel:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(40, 51, 136, 24);
        contentPane.add(lblNewLabel);

        hotelComboBox = new JComboBox<>();
        hotelComboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        hotelComboBox.setBounds(218, 50, 223, 31);
        contentPane.add(hotelComboBox);

        JLabel lblNewLabel_1 = new JLabel("I rate it: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(108, 120, 100, 24);
        contentPane.add(lblNewLabel_1);

        ratingComboBox = new JComboBox<>(new DefaultComboBoxModel<>(new Integer[] {1, 2, 3, 4, 5}));
        ratingComboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
        ratingComboBox.setBounds(218, 124, 190, 21);
        contentPane.add(ratingComboBox);

        JLabel lblNewLabel_2 = new JLabel("Stars");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setBounds(419, 120, 66, 24);
        contentPane.add(lblNewLabel_2);

        feedbackTextArea = new JTextArea();
        feedbackTextArea.setBounds(225, 199, 184, 100);
        contentPane.add(feedbackTextArea);

        JLabel lblNewLabel_3 = new JLabel("Feedback:");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_3.setBounds(105, 199, 88, 24);
        contentPane.add(lblNewLabel_3);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Rate r = new Rate(getSelectedHotel(), getCnum(), getSelectedRating(), getFeedback());
                    DatabaseHelper.insertRate(r);
                    CustomerView cv = new CustomerView(userPhoneNumber);
                    cv.setVisible(true);
                    dispose();
            }
        });
        submitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        submitButton.setBounds(418, 311, 94, 31);
        contentPane.add(submitButton);

        btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerView cv = new CustomerView(userPhoneNumber);
                cv.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton.setBounds(71, 317, 94, 25);
        contentPane.add(btnNewButton);

        updateHotelList();
    }

    public String getSelectedHotel() {
        return (String) hotelComboBox.getSelectedItem();
    }

    public int getSelectedRating() {
        return (int) ratingComboBox.getSelectedItem();
    }

    public String getFeedback() {
        return feedbackTextArea.getText();
    }

    public String getCnum() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String phoneNumber) {
        this.userPhoneNumber = phoneNumber;
    }

    public void addSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void setHotelOptions(String[] hotels) {
        hotelComboBox.setModel(new DefaultComboBoxModel<>(hotels));
    }

    public void updateHotelList() {
        String[] hotels = DatabaseHelper.getHotelNames();
        setHotelOptions(hotels);
    }
}

package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import model.DatabaseHelper;
import model.Hotel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HotelsView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<Hotel> hotelsList;
    private String userPhoneNumber;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HotelsView frame = new HotelsView();
                    frame.setVisible(true);
                    List<Hotel> hotels = DatabaseHelper.getHotelsWithRatings();
                    frame.updateHotelList(hotels);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public HotelsView(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        initialize();
        List<Hotel> hotels = DatabaseHelper.getHotelsWithRatings();
        updateHotelList(hotels);
    }

    public HotelsView() {
        initialize();
        List<Hotel> hotels = DatabaseHelper.getHotelsWithRatings();
        updateHotelList(hotels);
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 525);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Hotels and Ratings");
        lblNewLabel.setBounds(5, 5, 576, 20);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerView cv = new CustomerView(userPhoneNumber);
                cv.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton.setBounds(39, 442, 93, 36);
        contentPane.add(btnNewButton);

        hotelsList = new JList<>();
        hotelsList.setCellRenderer(new HotelListCellRenderer());
        hotelsList.setBounds(39, 35, 500, 400);
        contentPane.add(hotelsList);
    }

    public void updateHotelList(List<Hotel> hotels) {
        DefaultListModel<Hotel> listModel = new DefaultListModel<>();
        for (Hotel hotel : hotels) {
            listModel.addElement(hotel);
        }
        hotelsList.setModel(listModel);
    }
}

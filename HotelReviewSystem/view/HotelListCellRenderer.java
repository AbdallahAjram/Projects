package view;

import javax.swing.*;
import java.awt.*;
import model.Hotel;

public class HotelListCellRenderer extends JPanel implements ListCellRenderer<Hotel> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel ratingLabel;
    private JLabel emptyLabel;

    public HotelListCellRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        nameLabel = new JLabel();
        locationLabel = new JLabel();
        ratingLabel = new JLabel();
        emptyLabel = new JLabel(" ");

        add(nameLabel);
        add(locationLabel);
        add(ratingLabel);
        add(emptyLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Hotel> list, Hotel hotel, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText("Hotel Name: " + hotel.getName());
        locationLabel.setText("Country / Location: " + hotel.getLocation());
        ratingLabel.setText("Average Rating: " + String.format("%.2f", hotel.getAverageRating()));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}

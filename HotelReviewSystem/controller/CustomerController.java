package controller;

import model.Customer;
import model.DatabaseHelper;
import view.CustomerView;
import view.HotelView;
import view.RateView;
import view.HotelsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerController {

    private CustomerView customerView;
    private Customer customerModel;
    private DatabaseHelper dbHelper;

    public CustomerController(CustomerView customerView, Customer customerModel, DatabaseHelper dbHelper) {
        this.customerView = customerView;
        this.customerModel = customerModel;
        this.dbHelper = dbHelper;

        initController();
    }

    private void initController() {
        customerView.addAddHotelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelView hotelView = new HotelView();
                hotelView.setVisible(true);
                customerView.dispose();
            }
        });

        customerView.addRateHotelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RateView rateView = new RateView();
                rateView.setUserPhoneNumber(customerModel.getNumber());
                rateView.setVisible(true);
                customerView.dispose();
            }
        });

        customerView.addViewHotelsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelsView hotelsView = new HotelsView();
                hotelsView.setVisible(true);
                try {
					hotelsView.updateHotelList(dbHelper.getSortedHotelData());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                customerView.dispose();
            }
        });
    }
}

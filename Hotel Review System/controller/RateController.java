package controller;

import view.CustomerView;
import view.RateView;
import model.DatabaseHelper;
import model.Rate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RateController {
    private RateView rateView;
    private DatabaseHelper dbHelper;

    public RateController(RateView rateView, DatabaseHelper dbHelper) {
        this.rateView = rateView;
        this.dbHelper = dbHelper;
        initController();
    }

    private void initController() {
        rateView.updateHotelList();

        rateView.addSubmitButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Rate r = new Rate(rateView.getSelectedHotel(), rateView.getCnum(), rateView.getSelectedRating(), rateView.getFeedback());
                DatabaseHelper.insertRate(r);
                rateView.dispose();
                CustomerView cv = new CustomerView();
                cv.setVisible(true);
            }
        });
    }
}

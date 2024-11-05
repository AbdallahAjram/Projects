package controller;

import model.DatabaseHelper;
import view.LoginView;
import view.CustomerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        this.view.addLoginButtonListener(new LoginButtonListener());
    }

    class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String number = view.getNumber();
            String password = view.getPassword();

            if (DatabaseHelper.verifyCustomer(number, password)) {
                CustomerView customerView = new CustomerView();
                customerView.setVisible(true);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Invalid login credentials!");
            }
        }
    }
}

package controller;

import model.Customer;
import model.DatabaseHelper;
import view.SignupView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupController {
    private SignupView view;

    public SignupController(SignupView view) {
        this.view = view;
        this.view.addSignupButtonListener(new SignupButtonListener());
    }

    class SignupButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String number = view.getNumber();
            String name = view.getName();
            String password = view.getPassword();

            Customer customer = new Customer(number, name, password);
            DatabaseHelper.insertCustomer(customer);

            JOptionPane.showMessageDialog(view, "Signup successful!");
        }
    }
}

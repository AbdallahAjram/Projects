package model;

import java.sql.SQLException;

public class Flight {
    private int ID;
    private String airline_name;
    private String destination;
    private String departure_date; 
    private double price;
    

    public Flight(String airline_name, String destination, String departure_date, double price) {
        this.airline_name = airline_name;
        this.destination = destination;
        this.departure_date = departure_date;
        this.price = price;
        
    }

    public Flight() {}

    public int getFlight_id() {
        try {
            return DatabaseHelper.getFlightIdByDetails(airline_name, destination, departure_date);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setFlightID(int ID) {
        this.ID = ID;
    }

    public String getAirline_name() {
        return airline_name;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

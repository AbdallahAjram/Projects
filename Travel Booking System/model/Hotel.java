package model;

import java.sql.SQLException;

public class Hotel {
    private String name;
    private String location;
    private double price_per_night;
    private int id;

    public Hotel(String name, String location, double price_per_night) {
        this.name = name;
        this.location = location;
        this.price_per_night = price_per_night;
    }

    public Hotel() {
		
	}

    public int getHotel_id() {
        try {
			return DatabaseHelper.getHotelIdByDetails(name, location);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(double price_per_night) {
        this.price_per_night = price_per_night;
    }


	public void setHotelID(int int1) {
		this.id=int1;
	}
}

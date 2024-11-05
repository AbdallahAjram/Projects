package model;

import java.sql.SQLException;

public class Transport {
    private String type;
    private String location;
    private double price;
	private int id;

    public Transport(String type, String location, double price) {
        this.type = type;
        this.location = location;
        this.price = price;
    }


	public Transport() {
	}
    public int getTransport_id() {
        try {
			return DatabaseHelper.getTransportIdByDetails(type, location);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	public void setTransportID(int int1) {
		this.id=int1;
		
	}
}

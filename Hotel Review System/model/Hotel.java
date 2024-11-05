package model;


public class Hotel {
    private String name;
    private String location;
    private double averageRating;

    public Hotel(String name, double averageRating) {
        this.name = name;
        this.averageRating = averageRating;
    }
    public Hotel() {}

    public Hotel(String name, String location) {
        this.name=name;
        this.location=location;
    }
    

   
    public Hotel(String name, String location, double averageRating) {
		this.name = name;
		this.location = location;
		this.averageRating = averageRating;
	}
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location=location;
    }
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	@Override
    public String toString() {
        return name + " - " + String.format("%.2f", averageRating);
    }
}

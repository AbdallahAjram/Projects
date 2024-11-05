package model;

public class Booking {


    public enum Status {
        CONFIRMED,
        CANCELED
        
    }
    
    private int bookingId;
    private String userEmail;
    private String destination;
    private String flight;
    private String departDate;
    private String hotel;
    private String transportation;
    private Status status;

    public Booking(int bookingId, String userEmail, String destination, String flight, 
                   String departDate, String hotel, String transportation, Status status) {
        this.bookingId = bookingId;
        this.userEmail = userEmail;
        this.destination = destination;
        this.flight = flight;
        this.departDate = departDate;
        this.hotel = hotel;
        this.transportation = transportation;
        this.status = status;
    }

   
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

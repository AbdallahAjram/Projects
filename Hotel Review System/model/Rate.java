package model;

public class Rate {
    private String hotelName;
    private String customerNumber;
    private int rating;
    private String review;

    public Rate() {}

    public Rate(String hotelName, String customerNumber, int rating, String review) {
        this.hotelName=hotelName;
        this.customerNumber=customerNumber;
        this.rating=rating;
        this.review=review;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName=hotelName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber=customerNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating=rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review=review;
    }
}

package com.example.findmydorm;

import java.util.List;

public class DormItem {
    private int id;
    private String dormName;
    private double price;
    private String shortDescription;
    private String location;
    private List<String> images;
    private String ownerName;
    private String ownerPhone;

    public DormItem(int id,
                    String dormName,
                    double price,
                    String shortDescription,
                    String location,
                    List<String> images,
                    String ownerName,
                    String ownerPhone) {
        this.id                = id;
        this.dormName          = dormName;
        this.price             = price;
        this.shortDescription  = shortDescription;
        this.location          = location;
        this.images            = images;
        this.ownerName         = ownerName;
        this.ownerPhone        = ownerPhone;
    }

    // New getter for your id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDormName() { return dormName; }
    public double getPrice() { return price; }
    public String getShortDescription() { return shortDescription; }
    public String getLocation() { return location; }
    public List<String> getImages() { return images; }
    public String getOwnerName() { return ownerName; }
    public String getOwnerPhone() { return ownerPhone; }

    public void setDormName(String dormName) { this.dormName = dormName; }
    public void setPrice(double price) { this.price = price; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setLocation(String location) { this.location = location; }
    public void setImages(List<String> images) { this.images = images; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }

    @Override
    public String toString() {
        return "DormItem{" +
                "id=" + id +
                ", dormName='" + dormName + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                '}';
    }
}

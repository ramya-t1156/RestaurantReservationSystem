package com.org.restaurantReservationSystem.models;

public class DigitalProduct extends Product {
    private double fileSize; // File size in MB
    private String format;   // Format of the digital product (e.g., PDF, MP3)

    // Constructor with all fields, including 'id' for existing records
    public DigitalProduct(int id, String name, double price, int stockQuantity, double fileSize, String format) {
        super(id, name, price, stockQuantity);
        this.fileSize = fileSize;
        this.format = format;
    }

    // Constructor without 'id' for new records where the ID is auto-generated
    public DigitalProduct(String name, double price, int stockQuantity, double fileSize, String format) {
        super(name, price, stockQuantity);
        this.fileSize = fileSize;
        this.format = format;
    }

    // Getters and Setters
    public double getFileSize() { 
        return fileSize; 
    }

    public void setFileSize(double fileSize) { 
        this.fileSize = fileSize; 
    }

    public String getFormat() { 
        return format; 
    }

    public void setFormat(String format) { 
        this.format = format; 
    }

    public String getProductType() {
        return "Digital Product";
    }

    // Additional method for debugging or display purposes
    @Override
    public String toString() {
        return "DigitalProduct{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", stockQuantity=" + getStockQuantity() +
                ", fileSize=" + fileSize + " MB" +
                ", format='" + format + '\'' +
                '}';
    }
    
    public void displayProductDetails() {
        System.out.println("Digital Product Details:");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Stock Quantity: " + getStockQuantity());
    }
}

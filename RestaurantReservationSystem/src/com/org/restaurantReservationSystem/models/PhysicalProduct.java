package com.org.restaurantReservationSystem.models;

public class PhysicalProduct extends Product {
    private double weight;
    private double length;
    private double width;
    private double height;

    // Constructor with all fields
    public PhysicalProduct(int id, String name, double price, int stockQuantity, double weight, double length, double width, double height) {
        super(id, name, price, stockQuantity);
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // Constructor without 'id' (for cases where the ID is auto-generated)
    public PhysicalProduct(String name, double price, int stockQuantity, double weight, double length, double width, double height) {
        super(name, price, stockQuantity);
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // Getters and Setters for physical attributes
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public String getProductType() {
        return "Physical Product";
    }
    
    public void displayProductDetails() {
        System.out.println("Physical Product Details:");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Stock Quantity: " + getStockQuantity());
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Length: " + length + " cm");
        System.out.println("Width: " + width + " cm");
        System.out.println("Height: " + height + " cm");
    }
}

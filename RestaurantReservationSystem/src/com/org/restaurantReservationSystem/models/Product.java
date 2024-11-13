package com.org.restaurantReservationSystem.models;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stockQuantity;
    private double weight;
    private double length;
    private double width;
    private double height;
    
    public Product(int id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Constructor without 'id' (for cases where the ID is auto-generated)
    public Product(String name, double price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    // Constructor with all fields
    public Product(int id, String name, double price, int stockQuantity, double weight, double length, double width, double height) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // Constructor without 'id' (for cases where the ID is auto-generated)
    public Product(String name, double price, int stockQuantity, double weight, double length, double width, double height) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public void displayProductDetails() {
	}
    
}

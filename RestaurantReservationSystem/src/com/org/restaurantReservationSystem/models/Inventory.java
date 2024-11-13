package com.org.restaurantReservationSystem.models;

public class Inventory {

    // Attributes to represent inventory details
    private int inventoryId;
    private Product product;
    private int quantityInStock;

    // Constructor to initialize inventory with product and stock details
    public Inventory(int inventoryId, Product product, int quantityInStock) {
        this.inventoryId = inventoryId;
        this.product = product;
        this.quantityInStock = quantityInStock;
    }

    // Getter and setter for inventoryId
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    // Getter and setter for product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter and setter for quantityInStock
    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    // Method to update the inventory quantity by adding more stock
    public void updateStock(int quantity) {
        if (quantity > 0) {
            this.quantityInStock += quantity; // Increase stock quantity
            System.out.println("Stock updated. New quantity in stock: " + this.quantityInStock);
        } else {
            System.out.println("Invalid stock quantity!");
        }
    }

    // Method to decrease stock when a sale is made
    public void decreaseStock(int quantity) {
        if (quantity > 0 && this.quantityInStock >= quantity) {
            this.quantityInStock -= quantity; // Decrease stock quantity
            System.out.println("Stock updated. New quantity in stock: " + this.quantityInStock);
        } else {
            System.out.println("Insufficient stock or invalid quantity!");
        }
    }

    // Method to display the current inventory details
    public void displayInventory() {
        System.out.println("Inventory ID: " + inventoryId);
        System.out.println("Product ID: " + product.getId());
        System.out.println("Product Name: " + product.getName());
        System.out.println("Quantity in Stock: " + quantityInStock);
    }
}

package com.org.restaurantReservationSystem.models;

import java.util.Date;

public class ProductTransaction {
    private int transactionId;
    private int productId;
    private int quantity;
    private double pricePerUnit;
    private Date transactionDate;
    private String transactionType;  // E.g., "Sale" or "Purchase"
    private int supplierId;

    // Constructor
    public ProductTransaction(int transactionId, int productId, int quantity, double pricePerUnit, Date transactionDate, String transactionType, int supplierId) {
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.supplierId = supplierId;
    }

    // Getters and setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    // Method to display transaction details
    public void displayTransactionDetails() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Product ID: " + productId);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price Per Unit: " + pricePerUnit);
        System.out.println("Transaction Date: " + transactionDate);
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Supplier ID: " + supplierId);
    }
}

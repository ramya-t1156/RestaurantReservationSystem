package com.org.restaurantReservationSystem.models;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private int productId;
    private int quantity;
    private double pricePerUnit;
    private double totalAmount;
    private Date transactionDate;
    private int supplierId; // Added supplierId field
    private String transactionType; // Added to indicate whether it's a Sale or Purchase

    // Constructor with supplierId
    public Transaction(int transactionId, int productId, int quantity, double pricePerUnit, Date transactionDate, int supplierId, String transactionType) {
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.transactionDate = transactionDate;
        this.supplierId = supplierId;
        this.transactionType = transactionType; // Set the transaction type (Sale or Purchase)
        calculateTotalAmount(); // Calculate totalAmount based on quantity and pricePerUnit
    }

    // Getters and Setters for the fields

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
        calculateTotalAmount(); // Recalculate totalAmount when quantity changes
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        calculateTotalAmount(); // Recalculate totalAmount when price per unit changes
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // No need for setter for totalAmount, it’s auto-calculated

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getSupplierId() {
        return supplierId; // Getter for supplierId
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId; // Setter for supplierId
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Private method to calculate the totalAmount
    private void calculateTotalAmount() {
        this.totalAmount = this.quantity * this.pricePerUnit;
    }

    // Display transaction details
    public void displayTransactionDetails() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + "\n" +
               "Product ID: " + productId + "\n" +
               "Quantity: " + quantity + "\n" +
               "Price per Unit: " + pricePerUnit + "\n" +
               "Total Amount: " + totalAmount + "\n" +
               "Transaction Date: " + (transactionDate != null ? transactionDate.toString() : "N/A") + "\n" +
               "Supplier ID: " + supplierId + "\n" +  // Added supplierId in the toString
               "Transaction Type: " + transactionType;
    }
}

package com.org.restaurantReservationSystem.models;

import java.util.Date;

public class InventoryLog {
    private int logId;
    private int productId;
    private int quantityChange;  // Positive or negative change in stock
    private Date logDate;
    private String reason;  // Reason for change (e.g., "Purchase", "Sale")

    // Constructor
    public InventoryLog(int logId, int productId, int quantityChange, Date logDate, String reason) {
        this.logId = logId;
        this.productId = productId;
        this.quantityChange = quantityChange;
        this.logDate = logDate;
        this.reason = reason;
    }

    // Getters and setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // Method to display log details
    public void displayLogDetails() {
        System.out.println("Log ID: " + logId);
        System.out.println("Product ID: " + productId);
        System.out.println("Quantity Change: " + quantityChange);
        System.out.println("Log Date: " + logDate);
        System.out.println("Reason: " + reason);
    }
}

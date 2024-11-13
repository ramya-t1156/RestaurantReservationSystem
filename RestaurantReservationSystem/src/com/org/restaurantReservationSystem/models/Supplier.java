package com.org.restaurantReservationSystem.models;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private String contactNumber;
    private String emailAddress;
    private String address;

    // Constructor with all parameters
    public Supplier(int supplierId, String supplierName, String contactNumber, String emailAddress, String address) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    // Getter for supplierId
    public int getSupplierId() {
        return supplierId;
    }

    // Setter for supplierId
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    // Getter for supplierName
    public String getSupplierName() {
        return supplierName;
    }

    // Setter for supplierName
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Getter for contactNumber
    public String getContactNumber() {
        return contactNumber;
    }

    // Setter for contactNumber
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // Getter for emailAddress
    public String getEmailAddress() {
        return emailAddress;
    }

    // Setter for emailAddress
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Method to display supplier details
    public void displaySupplierDetails() {
        System.out.println("Supplier ID: " + supplierId);
        System.out.println("Supplier Name: " + supplierName);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Address: " + address);
    }

    // Method to update contact info
    public void updateContactInfo(String newContactNumber, String newEmailAddress) {
        this.contactNumber = newContactNumber;
        this.emailAddress = newEmailAddress;
    }

    // Method to update address
    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }
}

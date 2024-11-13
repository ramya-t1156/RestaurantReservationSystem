package com.org.restaurantReservationSystem.utils;

import com.org.restaurantReservationSystem.exceptions.InvalidStockQuantityException;
import com.org.restaurantReservationSystem.exceptions.InvalidPriceException;
import com.org.restaurantReservationSystem.exceptions.InvalidProductNameException;

public class InputValidator {

    // Validate product name
    public static void validateProductName(String productName) throws InvalidProductNameException {
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidProductNameException("Product name cannot be null or empty.");
        }
        if (productName.length() < 3 || productName.length() > 100) {
            throw new InvalidProductNameException("Product name must be between 3 and 100 characters.");
        }
    }

    // Validate price (positive value only)
    public static void validatePrice(double price) throws InvalidPriceException {
        if (price <= 0) {
            throw new InvalidPriceException("Price must be a positive number.");
        }
    }

    // Validate stock quantity (non-negative integer)
    public static void validateStockQuantity(int quantity) throws InvalidStockQuantityException {
        if (quantity < 0) {
            throw new InvalidStockQuantityException("Stock quantity cannot be negative.");
        }
    }

    // Validate product ID (should be a positive integer)
    public static void validateProductId(int productId) throws IllegalArgumentException {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive integer.");
        }
    }

    // Validate supplier name (should not be empty)
    public static void validateSupplierName(String supplierName) throws IllegalArgumentException {
        if (supplierName == null || supplierName.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        }
    }

    // General validation for any non-negative number
    public static void validateNonNegativeNumber(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }

    // Validate that a string is not null or empty
    public static void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }
}
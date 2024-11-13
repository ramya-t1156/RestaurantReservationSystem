package com.org.restaurantReservationSystem.exceptions;

// Custom exception for invalid product name
@SuppressWarnings("serial")
public class InvalidProductNameException extends Exception {

    // Constructor to initialize the exception message
    public InvalidProductNameException(String message) {
        super(message);
    }
}

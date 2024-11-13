package com.org.restaurantReservationSystem.exceptions;

// Custom exception for invalid product price
@SuppressWarnings("serial")
public class InvalidPriceException extends Exception {

    // Constructor to initialize the exception message
    public InvalidPriceException(String message) {
        super(message);
    }
}

package com.org.restaurantReservationSystem.exceptions;

@SuppressWarnings("serial")
public class InvalidProductException extends Exception {

    // Constructor that takes a message
    public InvalidProductException(String message) {
        super(message);
    }

    // Constructor that takes both a message and a cause (another throwable)
    public InvalidProductException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that takes only the cause
    public InvalidProductException(Throwable cause) {
        super(cause);
    }
}

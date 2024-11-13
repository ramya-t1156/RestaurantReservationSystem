package com.org.restaurantReservationSystem.exceptions;

@SuppressWarnings("serial")
public class InvalidTransactionException extends Exception {

    // Constructor that accepts a message
    public InvalidTransactionException(String message) {
        super(message);
    }

    // Constructor that accepts both a message and a cause (for chaining exceptions)
    public InvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.org.restaurantReservationSystem.exceptions;

@SuppressWarnings("serial")
public class InvalidStockQuantityException extends Exception {
    public InvalidStockQuantityException(String message) {
        super(message);
    }
}

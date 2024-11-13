package com.org.restaurantReservationSystem.exceptions;

@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

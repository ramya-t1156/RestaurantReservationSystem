package com.org.restaurantReservationSystem.threads;

import java.sql.SQLException;

import com.org.restaurantReservationSystem.dao.ProductDAO;
import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.exceptions.InvalidProductException;

public class ProductAdderThread extends Thread {

    private final Product product;

    // Constructor
    public ProductAdderThread(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        try {
            // Attempt to add the product to the database
            addProduct();
        } catch (InvalidProductException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    // Method to add the product to the database
    public void addProduct() throws InvalidProductException {
        // Validate the product details
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new InvalidProductException("Product name cannot be empty.");
        }
        if (product.getPrice() <= 0) {
            throw new InvalidProductException("Product price must be greater than zero.");
        }
        if (product.getStockQuantity() < 0) {
            throw new InvalidProductException("Product stock quantity cannot be negative.");
        }

        // Use the ProductDAO to add the product to the database
        ProductDAO productDAO = new ProductDAO();
        try {
            // Assuming addProduct now does not return a boolean but instead throws an exception if it fails
            productDAO.addProduct(product);
            System.out.println("Product added successfully: " + product.getName());
        } catch (SQLException e) {
            System.err.println("Database error while adding product: " + e.getMessage());
            throw new InvalidProductException("Error adding product to the database.", e);
        }
    }
}

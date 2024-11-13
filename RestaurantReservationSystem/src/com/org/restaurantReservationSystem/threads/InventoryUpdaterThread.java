package com.org.restaurantReservationSystem.threads;

import java.sql.SQLException;

import com.org.restaurantReservationSystem.dao.InventoryDAO;
import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.exceptions.InvalidStockQuantityException;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;

public class InventoryUpdaterThread extends Thread {

    private final Product product;
    private final int quantityChange;

    // Constructor
    public InventoryUpdaterThread(Product product, int quantityChange) {
        this.product = product;
        this.quantityChange = quantityChange;
    }

    @Override
    public void run() {
        try {
            // Perform inventory update
            try {
				updateInventory();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (InvalidStockQuantityException | ProductNotFoundException e) {
            System.err.println("Error updating inventory for Product ID " + product.getId() + ": " + e.getMessage());
        }
    }

    // Method to update the inventory in the database
    public void updateInventory() throws InvalidStockQuantityException, ProductNotFoundException, SQLException {
        // Validate the quantity change
        if (quantityChange <= 0) {
            throw new InvalidStockQuantityException("Stock quantity must be greater than zero.");
        }

        // Get the current inventory level
        InventoryDAO inventoryDAO = new InventoryDAO(null);
        Product currentProduct = null;
        currentProduct = inventoryDAO.getProductById(product.getId());

        if (currentProduct == null) {
            System.err.println("Product not found for Product ID " + product.getId());
            return;
        }

        // Update the stock quantity
        int updatedQuantity = currentProduct.getStockQuantity() + quantityChange;

        if (updatedQuantity < 0) {
            throw new InvalidStockQuantityException("Cannot update inventory. Insufficient stock.");
        }

        // Update the inventory in the database
        currentProduct.setStockQuantity(updatedQuantity);
        inventoryDAO.updateProduct(currentProduct);

        // Log the update
        System.out.println("Inventory updated successfully for Product ID " + product.getId() + ". New quantity: " + updatedQuantity);
    }
}

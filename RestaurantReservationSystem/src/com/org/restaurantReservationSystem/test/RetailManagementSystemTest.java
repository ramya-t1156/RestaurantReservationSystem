package com.org.restaurantReservationSystem.test;

import com.org.restaurantReservationSystem.models.*;
import com.org.restaurantReservationSystem.services.InventoryService;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;
import com.org.restaurantReservationSystem.exceptions.InvalidStockQuantityException;

public class RetailManagementSystemTest {
    public static void main(String[] args) {
        // Initialize service
        InventoryService inventoryService = new InventoryService(null); // Initialize the service

        // Create Physical and Digital Products
        Product physicalProduct = new PhysicalProduct(101, "Table", 200.00, 50, 10.5, 120.0, 80.0, 60.0);
        Product digitalProduct = new DigitalProduct(102, "Ebook", 15.00, 100, 0, "https://downloadlink.com/ebook");

        try {
            // Add products to inventory (InventoryService handles both Physical and Digital products)
            inventoryService.addProductToInventory(physicalProduct);  
            inventoryService.addProductToInventory(digitalProduct);  
            System.out.println("Products added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }

        // Display product details before stock update
        System.out.println("\nProduct Details Before Stock Update:");
        physicalProduct.displayProductDetails();
        digitalProduct.displayProductDetails();

        // Attempting to update stock for the products
        try {
            // Update stock for physical product (101) to 30
            inventoryService.updateStock(101, 30);  // Update the stock of physical product
            // Attempt to update stock for digital product (102) to a negative value (e.g., -20)
            inventoryService.updateStock(102, -20);  // Invalid stock quantity, should throw exception
            System.out.println("\nStock updated successfully!");
        } catch (ProductNotFoundException | InvalidStockQuantityException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }

        // Display product details after updating stock
        System.out.println("\nProduct Details After Stock Update:");
        try {
            // Fetch updated products from the service
            Product updatedPhysicalProduct = inventoryService.getProductById(101);
            Product updatedDigitalProduct = inventoryService.getProductById(102);

            updatedPhysicalProduct.displayProductDetails();
            updatedDigitalProduct.displayProductDetails();
        } catch (ProductNotFoundException e) {
            System.out.println("Error retrieving product details: " + e.getMessage());
        }
    }
}

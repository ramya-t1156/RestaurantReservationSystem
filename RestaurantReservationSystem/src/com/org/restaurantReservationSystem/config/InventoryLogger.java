package com.org.restaurantReservationSystem.config;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InventoryLogger {

    private static final String LOG_FILE = "inventory_log.txt";

    // Log a new event (e.g., product added, inventory updated)
    public static void logEvent(String event) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            printWriter.println(timestamp + " - " + event);
            System.out.println("Event logged: " + event); // Optional: Print to console as well
        } catch (IOException e) {
            System.out.println("Error logging event: " + e.getMessage());
        }
    }

    // Example of logging a product addition
    public static void logProductAddition(int productId, String productName) {
        String event = "Product Added: ID = " + productId + ", Name = " + productName;
        logEvent(event);
    }

    // Example of logging inventory update
    public static void logInventoryUpdate(int productId, int quantityChange) {
        String event = "Inventory Updated: Product ID = " + productId + ", Quantity Change = " + quantityChange;
        logEvent(event);
    }

    // Example of logging an error
    public static void logError(String errorMessage) {
        String event = "ERROR: " + errorMessage;
        logEvent(event);
    }

    // Example of logging a product removal (if your system supports removing products)
    public static void logProductRemoval(int productId, String productName) {
        String event = "Product Removed: ID = " + productId + ", Name = " + productName;
        logEvent(event);
    }
}

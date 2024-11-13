package com.org.restaurantReservationSystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.org.restaurantReservationSystem.dao.ProductDAO;
import com.org.restaurantReservationSystem.dao.SupplierDAO;
import com.org.restaurantReservationSystem.dao.TransactionDAO;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;
import com.org.restaurantReservationSystem.exceptions.InvalidStockQuantityException;
import com.org.restaurantReservationSystem.exceptions.InvalidTransactionException;
import com.org.restaurantReservationSystem.models.PhysicalProduct;
import com.org.restaurantReservationSystem.models.DigitalProduct;
import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.models.Transaction;
import com.org.restaurantReservationSystem.services.InventoryService;
import com.org.restaurantReservationSystem.services.TransactionService;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/restaurant_reservation_system";
            String username = "root";
            String password = "Rxmyah@1156may";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established.");

            ProductDAO productDAO = new ProductDAO();
            SupplierDAO supplierDAO = new SupplierDAO(connection);
            TransactionDAO transactionDAO = new TransactionDAO(connection);
            InventoryService inventoryService = new InventoryService(connection);
            TransactionService transactionService = new TransactionService(transactionDAO, productDAO, supplierDAO);

            Scanner scanner = new Scanner(System.in);
            int choice;

            while (true) {
                System.out.println("\n----- Inventory Management -----");
                System.out.println("1. Add Physical Product");
                System.out.println("2. Add Digital Product");
                System.out.println("3. Update Product Details");
                System.out.println("4. Update Inventory");
                System.out.println("5. Generate Inventory Report");
                System.out.println("6. Record Transaction");
                System.out.println("7. Generate Transaction Report");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter Product ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Product Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter Product Quantity: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Enter Product Weight (kg): ");
                        double weight = scanner.nextDouble();
                        System.out.print("Enter Product Length (cm): ");
                        double length = scanner.nextDouble();
                        System.out.print("Enter Product Width (cm): ");
                        double width = scanner.nextDouble();
                        System.out.print("Enter Product Height (cm): ");
                        double height = scanner.nextDouble();

                        PhysicalProduct physicalProduct = new PhysicalProduct(id, name, price, quantity, weight, length, width, height);
                        try {
                            inventoryService.addProductToInventory(physicalProduct);
                            System.out.println("Physical Product added successfully!");
                        } catch (Exception e) {
                            System.out.println("Error adding physical product: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Enter Product ID: ");
                        int digitalProductId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Product Name: ");
                        String digitalProductName = scanner.nextLine();
                        System.out.print("Enter Product Price: ");
                        double digitalProductPrice = scanner.nextDouble();
                        System.out.print("Enter Product Quantity: ");
                        int digitalProductQuantity = scanner.nextInt();
                        System.out.print("Enter File Size (MB): ");
                        double fileSize = scanner.nextDouble();
                        scanner.nextLine(); 
                        System.out.print("Enter Format (e.g., PDF, MP3): ");
                        String format = scanner.nextLine();

                        DigitalProduct digitalProduct = new DigitalProduct(digitalProductId, digitalProductName, digitalProductPrice, digitalProductQuantity, fileSize, format);
                        try {
                            inventoryService.addProductToInventory(digitalProduct);
                            System.out.println("Digital Product added successfully!");
                        } catch (Exception e) {
                            System.out.println("Error adding digital product: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("Enter Product ID to update: ");
                        int updateId1 = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            Product productToUpdate = inventoryService.getProductById(updateId1);
                            System.out.print("Enter new price: ");
                            double newPrice = scanner.nextDouble();
                            System.out.print("Enter new quantity: ");
                            int newQuantity = scanner.nextInt();

                            productToUpdate.setPrice(newPrice);
                            productToUpdate.setStockQuantity(newQuantity);
                            inventoryService.updateStock(updateId1, newQuantity);
                            System.out.println("Product details updated successfully!");
                        } catch (ProductNotFoundException e) {
                            System.out.println("Product not found!");
                        } catch (InvalidStockQuantityException e) {
                            System.out.println("Invalid stock quantity: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Enter Product ID to update stock: ");
                        int productId = scanner.nextInt();
                        System.out.print("Enter quantity to update: ");
                        int quantityChange = scanner.nextInt();
                        try {
                            inventoryService.updateStock(productId, quantityChange);
                            System.out.println("Inventory updated successfully!");
                        } catch (ProductNotFoundException | InvalidStockQuantityException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 5:
                        inventoryService.generateInventoryReport();
                        break;

                    case 6:
                        System.out.print("Enter Transaction ID: ");
                        int transactionId = scanner.nextInt();
                        System.out.print("Enter Product ID for transaction: ");
                        int productIdForTransaction = scanner.nextInt();
                        System.out.print("Enter Quantity: ");
                        int quantityForTransaction = scanner.nextInt();
                        System.out.print("Enter Price Per Unit: ");
                        double pricePerUnit = scanner.nextDouble();
                        scanner.nextLine(); 
                        System.out.print("Enter Transaction Type (Sale/Purchase): ");
                        String transactionType = scanner.nextLine();
                        System.out.print("Enter Supplier ID: ");
                        int supplierId = scanner.nextInt();
                        Date transactionDate = new Date(System.currentTimeMillis());

                        Transaction transaction = new Transaction(transactionId, productIdForTransaction, quantityForTransaction, pricePerUnit, transactionDate, supplierId, transactionType);

                        try {
                            transactionService.recordTransaction(transaction);
                            System.out.println("Transaction recorded successfully!");
                        } catch (ProductNotFoundException | InvalidTransactionException | SQLException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 7:
                        try {
                            transactionService.generateTransactionReport();
                        } catch (SQLException e) {
                            System.out.println("Error generating report: " + e.getMessage());
                        }
                        break;

                    case 8:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
    }
}

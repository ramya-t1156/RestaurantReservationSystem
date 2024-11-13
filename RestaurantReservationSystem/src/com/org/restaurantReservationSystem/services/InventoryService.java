package com.org.restaurantReservationSystem.services;

import com.org.restaurantReservationSystem.dao.InventoryDAO;
import com.org.restaurantReservationSystem.models.DigitalProduct;
import com.org.restaurantReservationSystem.models.PhysicalProduct;
import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.exceptions.InvalidStockQuantityException;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService(Connection connection) {
        this.inventoryDAO = new InventoryDAO(connection);
    }

    public void addProductToInventory(Product product) throws Exception {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        inventoryDAO.addProduct(product);
    }

    public void updateStock(int productId, int quantity) throws ProductNotFoundException, InvalidStockQuantityException {
        if (quantity < 0) throw new InvalidStockQuantityException("Quantity cannot be negative");

        Product product = inventoryDAO.getProductById(productId);
        product.setStockQuantity(quantity);
        try {
			inventoryDAO.updateProduct(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Product getProductById(int productId) throws ProductNotFoundException {
        return inventoryDAO.getProductById(productId);
    }

    public void generateInventoryReport() {
        try {
            List<Product> products = inventoryDAO.getAllProducts();
            System.out.println("=== Inventory Report ===");
            System.out.printf("%-10s %-20s %-10s %-10s\n", "Product ID", "Name", "Price", "Stock Quantity");
            System.out.println("----------------------------------------------------");
            
            for (Product product : products) {
                int productId;
                
                // Check if the product is an instance of PhysicalProduct or DigitalProduct
                if (product instanceof PhysicalProduct) {
                    productId = ((PhysicalProduct) product).getId();  // Cast to access getId()
                } else if (product instanceof DigitalProduct) {
                    productId = ((DigitalProduct) product).getId();   // Cast to access getId()
                } else {
                    continue;  // Skip if it's neither (safety check)
                }
                
                System.out.printf("%-10d %-20s %-10.2f %-10d\n", 
                                  productId, 
                                  product.getName(), 
                                  product.getPrice(), 
                                  product.getStockQuantity());
            }
        } catch (SQLException e) {
            System.out.println("Error generating inventory report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

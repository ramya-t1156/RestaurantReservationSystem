package com.org.restaurantReservationSystem.services;

import com.org.restaurantReservationSystem.dao.ProductDAO;
import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.models.PhysicalProduct;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;
import com.org.restaurantReservationSystem.exceptions.InvalidStockQuantityException;
import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private ProductDAO productDAO;

    // Constructor to initialize the ProductDAO
    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    // Add a new product to the inventory (specifically for PhysicalProduct)
    public void addProduct(String productName, double price, int stockQuantity, double weight, double length, double width, double height) {
        // Create a new PhysicalProduct
        Product product = new PhysicalProduct(0, productName, price, stockQuantity, weight, length, width, height);

        try {
            // Add the product to the database using the ProductDAO
            productDAO.addProduct(product);
            System.out.println("Product added successfully: " + productName);
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    // Update product details in the inventory
    public void updateProduct(int productId, String productName, double price, int stockQuantity, double weight, double length, double width, double height) throws ProductNotFoundException {
        try {
            // Retrieve the product from the database
            Product product = productDAO.getProductById(productId);

            if (product == null) {
                throw new ProductNotFoundException("Product not found with ID: " + productId);
            }

            // Update the common fields
            product.setName(productName);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);

            // Check if the product is an instance of PhysicalProduct
            if (product instanceof PhysicalProduct) {
                PhysicalProduct physicalProduct = (PhysicalProduct) product;

                // Set specific fields for PhysicalProduct
                physicalProduct.setWeight(weight);
                physicalProduct.setLength(length);
                physicalProduct.setWidth(width);
                physicalProduct.setHeight(height);

                // Update the product in the database (using the same DAO method)
                productDAO.updateProduct(physicalProduct);
                System.out.println("Physical product updated successfully: " + productName);
            } else {
                // Handle other product types if needed
                System.out.println("Unsupported product type for update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }


    // Get product details by product ID
    public Product getProductById(int productId) throws ProductNotFoundException {
        try {
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                throw new ProductNotFoundException("Product not found with ID: " + productId);
            }
            return product;
        } catch (SQLException e) {
            System.out.println("Error retrieving product: " + e.getMessage());
            return null;
        }
    }

    // Get all products in the inventory
    public List<Product> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            System.out.println("Error retrieving all products: " + e.getMessage());
            return null;
        }
    }

    // Delete a product from the inventory
    public void deleteProduct(int productId) throws ProductNotFoundException {
        try {
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                throw new ProductNotFoundException("Product not found with ID: " + productId);
            }

            productDAO.deleteProduct(productId);
            System.out.println("Product deleted successfully: ID = " + productId);
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    // Update stock quantity for a specific product
    public void updateStock(int productId, int quantityChange) throws InvalidStockQuantityException, ProductNotFoundException {
        try {
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                throw new ProductNotFoundException("Product not found with ID: " + productId);
            }

            // Ensure stock is not negative
            if (product.getStockQuantity() + quantityChange < 0) {
                throw new InvalidStockQuantityException("Cannot reduce stock below 0");
            }

            product.setStockQuantity(product.getStockQuantity() + quantityChange);
            productDAO.updateProduct(product);

            System.out.println("Product stock updated: ID = " + productId + ", New Quantity = " + product.getStockQuantity());
        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
    }
}

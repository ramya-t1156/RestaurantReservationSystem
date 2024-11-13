package com.org.restaurantReservationSystem.dao;

import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.models.PhysicalProduct;
import com.org.restaurantReservationSystem.models.DigitalProduct;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private Connection connection;

    // Constructor that accepts a Connection object
    public InventoryDAO(Connection connection) {
        this.connection = connection;
    }

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, stock_quantity, weight, length, width, height) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStockQuantity());
            if (product instanceof PhysicalProduct) {
                statement.setDouble(4, ((PhysicalProduct) product).getWeight());
                statement.setDouble(5, ((PhysicalProduct) product).getLength());
                statement.setDouble(6, ((PhysicalProduct) product).getWidth());
                statement.setDouble(7, ((PhysicalProduct) product).getHeight());
            } else if (product instanceof DigitalProduct) {
                statement.setNull(4, Types.DOUBLE);  // No weight for digital products
                statement.setNull(5, Types.DOUBLE);  // No length for digital products
                statement.setNull(6, Types.DOUBLE);  // No width for digital products
                statement.setNull(7, Types.DOUBLE);  // No height for digital products
            }
            statement.executeUpdate();
        }
    }

    public Product getProductById(int productId) throws ProductNotFoundException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToProduct(resultSet);
            } else {
                throw new ProductNotFoundException("Product not found with ID: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductNotFoundException("Error retrieving product from the database.");
        }
    }

    private Product mapRowToProduct(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("product_id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        int stockQuantity = resultSet.getInt("stock_quantity");
        

            // It's a physical product
            double weight = resultSet.getDouble("weight");
            double length = resultSet.getDouble("length");
            double width = resultSet.getDouble("width");
            double height = resultSet.getDouble("height");
            return new PhysicalProduct(productId, name, price, stockQuantity, weight, length, width, height);
        
    }

    public void updateProduct(Product product) throws SQLException {
        // Update the product in the database (example for physical products, similar for digital)
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getStockQuantity());
            statement.setInt(2, product.getId());
            statement.executeUpdate();
        }
    }
    
 // Method to retrieve all products from the database
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Product product = mapRowToProduct(resultSet);
                products.add(product);
            }
        }
        return products;
    }
}

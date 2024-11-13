package com.org.restaurantReservationSystem.dao;

import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection connection;

    public ProductDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new product
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, stock_quantity, weight, length, width, height) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStockQuantity());
            statement.setDouble(4, product.getWeight());
            statement.setDouble(5, product.getLength());
            statement.setDouble(6, product.getWidth());
            statement.setDouble(7, product.getHeight());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added successfully: " + product);
            } else {
                System.out.println("Failed to add product.");
            }
        }
    }

    // Update an existing product
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, stock_quantity = ?, weight = ?, length = ?, width = ?, height = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStockQuantity());
            statement.setDouble(4, product.getWeight());
            statement.setDouble(5, product.getLength());
            statement.setDouble(6, product.getWidth());
            statement.setDouble(7, product.getHeight());
            statement.setInt(8, product.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product updated successfully: " + product);
            } else {
                System.out.println("Failed to update product.");
            }
        }
    }

    // Get a product by ID
    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getDouble("weight"),
                        resultSet.getDouble("length"),
                        resultSet.getDouble("width"),
                        resultSet.getDouble("height")
                );
            } else {
                System.out.println("Product with ID " + productId + " not found.");
                return null;
            }
        }
    }

    // Get all products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getDouble("weight"),
                        resultSet.getDouble("length"),
                        resultSet.getDouble("width"),
                        resultSet.getDouble("height")
                ));
            }
        }
        return products;
    }

    // Delete a product
    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully: ID = " + productId);
            } else {
                System.out.println("Failed to delete product.");
            }
        }
    }

    // Close the database connection
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

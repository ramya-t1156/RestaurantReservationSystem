package com.org.restaurantReservationSystem.dao;

import com.org.restaurantReservationSystem.models.ProductTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductTransactionsDAO {

    private Connection connection;

    public ProductTransactionsDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new product transaction
    public void addProductTransaction(ProductTransaction transaction) throws SQLException {
        String sql = "INSERT INTO product_transactions (product_id, transaction_type, quantity, price_per_unit, transaction_date, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getProductId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setInt(3, transaction.getQuantity());
            stmt.setDouble(4, transaction.getPricePerUnit());
            stmt.setTimestamp(5, new Timestamp(transaction.getTransactionDate().getTime())); // Convert Date to Timestamp
            stmt.setInt(6, transaction.getSupplierId());
            stmt.executeUpdate();
        }
    }

    // Get all product transactions
    public List<ProductTransaction> getAllProductTransactions() throws SQLException {
        List<ProductTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM product_transactions";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Correctly fetch the product transaction details from the ResultSet
                transactions.add(new ProductTransaction(
                        rs.getInt("transaction_id"),        // Assuming you have transaction_id in your table
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_per_unit"),     // Add price per unit if needed
                        rs.getTimestamp("transaction_date"), // This fetches the timestamp
                        rs.getString("transaction_type"),   // Assuming you have transaction_type column
                        rs.getInt("supplier_id")            // Assuming you have supplier_id column
                ));
            }
        }
        return transactions;
    }

    // Get a product transaction by ID
    public ProductTransaction getProductTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM product_transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductTransaction(
                            rs.getInt("transaction_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price_per_unit"),
                            rs.getTimestamp("transaction_date"),
                            rs.getString("transaction_type"),
                            rs.getInt("supplier_id")
                    );
                }
            }
        }
        return null;  // Return null if no transaction found
    }
}

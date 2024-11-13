package com.org.restaurantReservationSystem.dao;

import com.org.restaurantReservationSystem.models.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection connection;

    // Constructor that initializes the database connection
    public TransactionDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new transaction to the database
    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (product_id, quantity, price_per_unit, total_amount, transaction_date, transaction_type, supplier_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getProductId());
            stmt.setInt(2, transaction.getQuantity());
            stmt.setDouble(3, transaction.getPricePerUnit());
            stmt.setDouble(4, transaction.getTotalAmount());
            stmt.setDate(5, new java.sql.Date(transaction.getTransactionDate().getTime())); // Convert Date to SQL Date
            stmt.setString(6, transaction.getTransactionType());
            stmt.setInt(7, transaction.getSupplierId()); // Set supplierId
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding transaction: " + e.getMessage());
            throw e; // Propagate exception
        }
    }


    // Retrieve a transaction by its ID
    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTransaction(rs);
            }
        }
        return null; // No matching transaction found
    }

    // Retrieve all transactions
    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        }
        return transactions;
    }


    // Retrieve transactions for a specific product
    public List<Transaction> getTransactionsForProduct(int productId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        }
        return transactions;
    }

    // Retrieve transactions for a specific supplier
    public List<Transaction> getTransactionsForSupplier(int supplierId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        }
        return transactions;
    }

    // Update a transaction
    public void updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET product_id = ?, quantity = ?, price_per_unit = ?, total_amount = ?, transaction_date = ?, transaction_type = ?, supplier_id = ? "
                   + "WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getProductId());
            stmt.setInt(2, transaction.getQuantity());
            stmt.setDouble(3, transaction.getPricePerUnit());
            stmt.setDouble(4, transaction.getTotalAmount());
            stmt.setDate(5, new java.sql.Date(transaction.getTransactionDate().getTime()));
            stmt.setString(6, transaction.getTransactionType());
            stmt.setInt(7, transaction.getSupplierId());
            stmt.setInt(8, transaction.getTransactionId());
            stmt.executeUpdate();
        }
    }

    // Delete a transaction by ID
    public void deleteTransaction(int transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
        }
    }

    // Helper method to map ResultSet to a Transaction object
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        int transactionId = rs.getInt("transaction_id"); // Ensure correct column name
        int productId = rs.getInt("product_id");
        int quantity = rs.getInt("quantity");
        double pricePerUnit = rs.getDouble("price_per_unit");
        @SuppressWarnings("unused")
		double totalAmount = rs.getDouble("total_amount");
        Date transactionDate = rs.getDate("transaction_date");
        String transactionType = rs.getString("transaction_type");
        int supplierId = rs.getInt("supplier_id"); // Ensure this exists in the ResultSet
        return new Transaction(transactionId, productId, quantity, pricePerUnit, transactionDate, supplierId, transactionType);
    }

}

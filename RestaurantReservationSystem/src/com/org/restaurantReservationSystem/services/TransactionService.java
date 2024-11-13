package com.org.restaurantReservationSystem.services;

import com.org.restaurantReservationSystem.dao.TransactionDAO;
import com.org.restaurantReservationSystem.dao.ProductDAO;
import com.org.restaurantReservationSystem.dao.SupplierDAO;
import com.org.restaurantReservationSystem.models.Transaction;
import com.org.restaurantReservationSystem.models.Product;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;
import com.org.restaurantReservationSystem.exceptions.InvalidTransactionException;

import java.sql.SQLException;
import java.util.List;

public class TransactionService {

    private TransactionDAO transactionDAO;
    private ProductDAO productDAO;
    // Constructor to inject DAO dependencies
    public TransactionService(TransactionDAO transactionDAO, ProductDAO productDAO, SupplierDAO supplierDAO) {
        this.transactionDAO = transactionDAO;
        this.productDAO = productDAO;
    }

    // Record a new transaction (Purchase or Sale)
    public void recordTransaction(Transaction transaction) throws SQLException, ProductNotFoundException, InvalidTransactionException {
        // Validate that the product exists
        Product product = productDAO.getProductById(transaction.getProductId());
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + transaction.getProductId() + " not found.");
        }

        // Validate transaction type and quantity
        if (transaction.getTransactionType().equals("Sale") && product.getStockQuantity() < transaction.getQuantity()) {
            throw new InvalidTransactionException("Not enough stock for the sale.");
        }

        if (transaction.getTransactionType().equals("Purchase") && transaction.getQuantity() <= 0) {
            throw new InvalidTransactionException("Purchase quantity must be greater than zero.");
        }

        // Add the transaction to the database
        transactionDAO.addTransaction(transaction);

        // Update the stock based on transaction type
        updateStockOnTransaction(transaction);
    }

    // Get a transaction by ID
    public Transaction getTransactionById(int transactionId) throws SQLException {
        return transactionDAO.getTransactionById(transactionId);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionDAO.getAllTransactions();
    }

    // Get all transactions for a specific product
    public List<Transaction> getTransactionsForProduct(int productId) throws SQLException {
        return transactionDAO.getTransactionsForProduct(productId);
    }

    // Get all transactions for a specific supplier
    public List<Transaction> getTransactionsForSupplier(int supplierId) throws SQLException {
        return transactionDAO.getTransactionsForSupplier(supplierId);
    }

    // Update stock based on the transaction type (Purchase or Sale)
    public void updateStockOnTransaction(Transaction transaction) throws ProductNotFoundException, SQLException {
        Product product = productDAO.getProductById(transaction.getProductId());
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + transaction.getProductId() + " not found.");
        }

        // Adjust stock based on transaction type
        if (transaction.getTransactionType().equals("Sale")) {
            product.setStockQuantity(product.getStockQuantity() - transaction.getQuantity());
        } else if (transaction.getTransactionType().equals("Purchase")) {
            product.setStockQuantity(product.getStockQuantity() + transaction.getQuantity());
        }

        // Update the product stock in the database
        productDAO.updateProduct(product);
    }

    // Generate a summary report of all transactions
    public void generateTransactionReport() throws SQLException {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction : transactions) {
            System.out.println("Transaction ID: " + transaction.getTransactionId());
            System.out.println("Product ID: " + transaction.getProductId());
            System.out.println("Supplier ID: " + transaction.getSupplierId());
            System.out.println("Quantity: " + transaction.getQuantity());
            System.out.println("Transaction Type: " + transaction.getTransactionType());
            System.out.println("Transaction Date: " + transaction.getTransactionDate());
            System.out.println("---------------------------");
        }
    }

    // Update transaction details (e.g., updating the transaction quantity)
    public void updateTransaction(Transaction transaction) throws SQLException, ProductNotFoundException {
        // Check if the transaction exists
        Transaction existingTransaction = transactionDAO.getTransactionById(transaction.getTransactionId());
        if (existingTransaction == null) {
            throw new SQLException("Transaction not found with ID: " + transaction.getTransactionId());
        }

        // Update the transaction in the database
        transactionDAO.updateTransaction(transaction);

        // Update the stock based on the new transaction details
        updateStockOnTransaction(transaction);
    }

    // Delete a transaction by ID
    public void deleteTransaction(int transactionId) throws SQLException {
        // Check if the transaction exists
        Transaction existingTransaction = null;
		try {
			existingTransaction = transactionDAO.getTransactionById(transactionId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (existingTransaction == null) {
            throw new SQLException("Transaction not found with ID: " + transactionId);
        }

        // Delete the transaction from the database
        transactionDAO.deleteTransaction(transactionId);

        // Revert the stock change based on the deleted transaction type
        try {
			revertStockOnTransaction(existingTransaction);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Revert stock when a transaction is deleted
    private void revertStockOnTransaction(Transaction transaction) throws SQLException, ProductNotFoundException {
        Product product = productDAO.getProductById(transaction.getProductId());
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + transaction.getProductId() + " not found.");
        }

        // Revert the stock change based on transaction type
        if (transaction.getTransactionType().equals("Sale")) {
            product.setStockQuantity(product.getStockQuantity() + transaction.getQuantity());
        } else if (transaction.getTransactionType().equals("Purchase")) {
            product.setStockQuantity(product.getStockQuantity() - transaction.getQuantity());
        }

        // Update the product stock in the database
        productDAO.updateProduct(product);
    }
}

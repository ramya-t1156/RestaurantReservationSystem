package com.org.restaurantReservationSystem.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.org.restaurantReservationSystem.models.InventoryLog;

public class InventoryLogDAO {

    private Connection connection;

    public InventoryLogDAO(Connection connection) {
        this.connection = connection;
    }

    // Add an inventory log entry
    public void addInventoryLog(InventoryLog log) throws SQLException {
        String sql = "INSERT INTO inventory_log (product_id, quantity_change, log_date, reason) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, log.getProductId());
            stmt.setInt(2, log.getQuantityChange());
            stmt.setTimestamp(3, new Timestamp(log.getLogDate().getTime()));  // Set the log date correctly
            stmt.setString(4, log.getReason());
            stmt.executeUpdate();
        }
    }

    // Get all inventory logs
    public List<InventoryLog> getAllInventoryLogs() throws SQLException {
        List<InventoryLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM inventory_log";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                logs.add(new InventoryLog(
                        rs.getInt("log_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity_change"),
                        rs.getTimestamp("log_date"),  // Fetch the timestamp correctly
                        rs.getString("reason")        // Fetch reason for the change
                ));
            }
        }
        return logs;
    }

    // Get inventory log by ID
    public InventoryLog getInventoryLogById(int logId) throws SQLException {
        String sql = "SELECT * FROM inventory_log WHERE log_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, logId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new InventoryLog(
                            rs.getInt("log_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity_change"),
                            rs.getTimestamp("log_date"),  // Fetch timestamp
                            rs.getString("reason")        // Fetch reason for change
                    );
                }
            }
        }
        return null; // Return null if no log entry found
    }
}

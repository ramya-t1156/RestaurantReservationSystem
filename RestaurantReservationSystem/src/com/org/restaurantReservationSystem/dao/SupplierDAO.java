package com.org.restaurantReservationSystem.dao;

import com.org.restaurantReservationSystem.models.Supplier;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    private Connection connection;

    public SupplierDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new supplier
    public void addSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO suppliers (name, contact_number, email, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactNumber());
            stmt.setString(3, supplier.getEmailAddress());
            stmt.setString(4, supplier.getAddress());
            stmt.executeUpdate();
        }
    }

    // Get a supplier by ID
    public Supplier getSupplierById(int supplierId) throws SQLException, ProductNotFoundException {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_number"),
                        rs.getString("email"),
                        rs.getString("address")
                    );
                } else {
                    throw new ProductNotFoundException("Supplier not found with ID: " + supplierId);
                }
            }
        }
    }

    // Update a supplier
    public void updateSupplier(Supplier supplier) throws SQLException {
        String sql = "UPDATE suppliers SET name = ?, contact_number = ?, email = ?, address = ? WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactNumber());
            stmt.setString(3, supplier.getEmailAddress());
            stmt.setString(4, supplier.getAddress());
            stmt.setInt(5, supplier.getSupplierId());
            stmt.executeUpdate();
        }
    }

    // Delete a supplier
    public void deleteSupplier(int supplierId) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            stmt.executeUpdate();
        }
    }

    // Get all suppliers
    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("contact_number"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
        }
        return suppliers;
    }
}

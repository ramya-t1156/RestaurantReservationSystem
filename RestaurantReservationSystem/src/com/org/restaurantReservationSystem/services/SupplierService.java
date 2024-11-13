package com.org.restaurantReservationSystem.services;

import com.org.restaurantReservationSystem.dao.SupplierDAO;
import com.org.restaurantReservationSystem.exceptions.ProductNotFoundException;
import com.org.restaurantReservationSystem.models.Supplier;

import java.sql.SQLException;
import java.util.List;

public class SupplierService {

    private SupplierDAO supplierDAO;

    public SupplierService(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    // Add a new supplier
    public void addSupplier(Supplier supplier) {
        try {
            supplierDAO.addSupplier(supplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update supplier details
    public void updateSupplier(Supplier supplier) {
        try {
            supplierDAO.updateSupplier(supplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get supplier by ID
    public Supplier getSupplierById(int supplierId) throws ProductNotFoundException {
        try {
            return supplierDAO.getSupplierById(supplierId);
        } catch (SQLException e) {
            throw new ProductNotFoundException("Supplier not found with ID: " + supplierId);
        }
    }

    // Delete supplier
    public void deleteSupplier(int supplierId) throws ProductNotFoundException {
        try {
            supplierDAO.deleteSupplier(supplierId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductNotFoundException("Supplier not found with ID: " + supplierId);
        }
    }

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        try {
            return supplierDAO.getAllSuppliers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

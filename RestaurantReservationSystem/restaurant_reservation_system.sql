-- Create the database
CREATE DATABASE restaurant_reservation_system;
USE restaurant_reservation_system;

-- Table for Products
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    weight DECIMAL(10, 2) DEFAULT 0,
    length DECIMAL(10, 2) DEFAULT 0,
    width DECIMAL(10, 2) DEFAULT 0,
    height DECIMAL(10, 2) DEFAULT 0
);
SELECT * FROM products;

-- Table for Suppliers
CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_number VARCHAR(15),
    email VARCHAR(255),
    address TEXT
);
SELECT * FROM suppliers;

-- Table for Transactions
CREATE TABLE  transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    supplier_id INT,
    quantity INT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);
SELECT * FROM transactions;

-- Table for Inventory logs (for auditing updates to inventory)
CREATE TABLE inventory_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    action VARCHAR(50),  -- 'Added', 'Removed', 'Updated'
    change_quantity INT,
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Table for Product Transactions (for tracking purchase or sale)
CREATE TABLE product_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    transaction_type ENUM('Purchase', 'Sale') NOT NULL,
    quantity INT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Table for System Users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Manager', 'Staff') NOT NULL
);

-- Table for Product Categories
CREATE TABLE  product_categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Table for Transaction Details (e.g., individual line items in an order)
CREATE TABLE transaction_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
SELECT * FROM transaction_details;

-- Adding indexes to speed up queries
CREATE INDEX IF NOT EXISTS idx_products_name ON products(name);
CREATE INDEX IF NOT EXISTS idx_transactions_date ON transactions(transaction_date);
CREATE INDEX IF NOT EXISTS idx_inventory_log_date ON inventory_log(log_date);

-- Triggers for Inventory Updates:
-- Trigger to update the stock_quantity when a product is purchased
DELIMITER $$
CREATE TRIGGER after_purchase_update_stock
AFTER INSERT ON product_transactions
FOR EACH ROW
BEGIN
    IF NEW.transaction_type = 'Purchase' THEN
        UPDATE products 
        SET stock_quantity = stock_quantity + NEW.quantity
        WHERE product_id = NEW.product_id;
        
        -- Insert into inventory log for auditing
        INSERT INTO inventory_log (product_id, action, change_quantity)
        VALUES (NEW.product_id, 'Added', NEW.quantity);
    END IF;
END$$
DELIMITER ;

-- Trigger to update the stock_quantity when a product is sold
DELIMITER $$
CREATE TRIGGER after_sale_update_stock
AFTER INSERT ON product_transactions
FOR EACH ROW
BEGIN
    IF NEW.transaction_type = 'Sale' THEN
        UPDATE products 
        SET stock_quantity = stock_quantity - NEW.quantity
        WHERE product_id = NEW.product_id;
        
        -- Insert into inventory log for auditing
        INSERT INTO inventory_log (product_id, action, change_quantity)
        VALUES (NEW.product_id, 'Removed', NEW.quantity);
    END IF;
END$$
DELIMITER ;

-- Trigger to prevent sale if stock is insufficient
DELIMITER $$
CREATE TRIGGER check_stock_before_sale
BEFORE INSERT ON product_transactions
FOR EACH ROW
BEGIN
    DECLARE available_stock INT;
    SELECT stock_quantity INTO available_stock
    FROM products
    WHERE product_id = NEW.product_id;
    
    IF NEW.transaction_type = 'Sale' AND available_stock < NEW.quantity THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient stock for sale transaction';
    END IF;
END$$
DELIMITER ;

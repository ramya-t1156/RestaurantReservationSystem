package com.org.restaurantReservationSystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant_reservation_system", "root", "Rxmyah@1156may");
    }
}

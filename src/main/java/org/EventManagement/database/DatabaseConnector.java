package org.EventManagement.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String url = "jdbc:mysql://localhost:3306/event_management";
    private static final String userName = "root";
    private static final String password = "5hnMMfr)begRARS";

    public static Connection getConnection() throws SQLException{
        try {
            return DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw e;
        }
    }
}

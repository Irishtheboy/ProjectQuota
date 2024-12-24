package DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DBConnection {

    private static Connection connection;

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Start the Apache Derby Network Server
                org.apache.derby.drda.NetworkServerControl server =
                    new org.apache.derby.drda.NetworkServerControl(InetAddress.getByName("localhost"), 1527);
                server.start(new PrintWriter(System.out, true));

                // Wait a moment to ensure the server starts
                Thread.sleep(2000); // Optional: Adjust based on startup time

                // Load the database driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                // Connect to the database
                String dbURL = "jdbc:derby://localhost:1527/QuotaDatabase;create=true";
                String username = "admini";
                String password = "admin";

                connection = DriverManager.getConnection(dbURL, username, password);
                System.out.println("Connected to the Derby database successfully.");

                // Create tables if not exists
                createTablesIfNotExist();
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Failed to start the server or connect to the database: " + e.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to check and create tables if they don't exist
    private static void createTablesIfNotExist() {
        try (Statement stmt = connection.createStatement()) {
            // Create CLIENTS table if it doesn't exist
            String createClientsTable = "CREATE TABLE CLIENTS ("
                    + "CLIENT_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "NAME VARCHAR(100), "
                    + "CONTACT_NUMBER VARCHAR(20), "
                    + "EMAIL VARCHAR(100))";
            executeTableCreationIfNotExist(stmt, createClientsTable);

            // Create EMPLOYEES table if it doesn't exist
            String createEmployeesTable = "CREATE TABLE EMPLOYEES ("
                    + "EMPLOYEE_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "FIRST_NAME VARCHAR(50), "
                    + "LAST_NAME VARCHAR(50), "
                    + "USERNAME VARCHAR(50), "
                    + "PASSWORD VARCHAR(255), "
                    + "EMAIL VARCHAR(255), "
                    + "ROLE VARCHAR(20))";
            executeTableCreationIfNotExist(stmt, createEmployeesTable);

            // Create QUOTES table if it doesn't exist
            String createQuotesTable = "CREATE TABLE QUOTES ("
                    + "QUOTE_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "CLIENT_NAME VARCHAR(100) NOT NULL, "
                    + "EMAIL VARCHAR(255) NOT NULL, "
                    + "CONTACT_NUMBER VARCHAR(15) NOT NULL, "
                    + "QUOTE_DATE DATE NOT NULL, "
                    + "TOTAL_AMOUNT DOUBLE NOT NULL)";
            executeTableCreationIfNotExist(stmt, createQuotesTable);

            // Create QUOTE_ITEMS table if it doesn't exist
            String createQuoteItemsTable = "CREATE TABLE QUOTE_ITEMS ("
                    + "QUOTE_ITEM_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "QUOTE_ID INT NOT NULL, "
                    + "PART_NAME VARCHAR(100) NOT NULL, "
                    + "QUANTITY INT NOT NULL, "
                    + "PRICE_PER_UNIT DOUBLE NOT NULL, "
                    + "TOTAL DOUBLE NOT NULL)";
            executeTableCreationIfNotExist(stmt, createQuoteItemsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to execute table creation query if the table doesn't exist
    private static void executeTableCreationIfNotExist(Statement stmt, String query) throws SQLException {
        try {
            stmt.executeUpdate(query);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            if ("X0Y32".equals(e.getSQLState())) {
                System.out.println("Table already exists.");
            } else {
                throw e;
            }
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connected successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

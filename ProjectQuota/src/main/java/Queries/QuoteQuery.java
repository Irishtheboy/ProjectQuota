package Queries;

import DatabaseConnection.DBConnection;
import java.sql.*;

public class QuoteQuery {

    // Method to insert a quote and return the generated quote ID
    public static int insertQuote(String clientName, String contactNumber, String email, String quoteDate, double totalAmount) throws SQLException {
        String sql = "INSERT INTO quotes (client_name, email, contact_number, quote_date, total_amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, clientName);
            stmt.setString(2, email);
            stmt.setString(3, contactNumber);
            stmt.setString(4, quoteDate);
            stmt.setDouble(5, totalAmount);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating quote failed, no rows affected.");
            }

            // Retrieve generated quote ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating quote failed, no ID obtained.");
                }
            }
        }
    }

    // Method to insert quote items
    public static void insertQuoteItems(int quoteId, Object[][] quoteItems) throws SQLException {
        String sql = "INSERT INTO quote_items (quote_id, part_name, quantity, price_per_unit, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Object[] item : quoteItems) {
                stmt.setInt(1, quoteId);               // Quote ID
                stmt.setString(2, (String) item[0]);  // Part Name
                stmt.setInt(3, (int) item[1]);        // Quantity
                stmt.setDouble(4, (double) item[2]);  // Price per Unit
                stmt.setDouble(5, (double) item[3]);  // Total
                stmt.addBatch();
            }
            stmt.executeBatch();  // Execute the batch insert
        }
    }
}

package Queries;

import DatabaseConnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientQueries {

    // Method to retrieve sold items history from the database
    public static ArrayList<String[]> getSoldItemsHistory(String searchQuery) throws SQLException {
        ArrayList<String[]> soldItems = new ArrayList<>();
        
        // SQL query to fetch sold items based on client name search query
        String sql = "SELECT q.QUOTE_ID, q.CLIENT_NAME, q.QUOTE_DATE, qi.PART_NAME, qi.QUANTITY, qi.TOTAL "
                   + "FROM QUOTES q JOIN QUOTE_ITEMS qi ON q.QUOTE_ID = qi.QUOTE_ID "
                   + "WHERE q.CLIENT_NAME LIKE ?";
        
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchQuery + "%"); // Search for client names matching the query
            ResultSet rs = stmt.executeQuery();
            
            // Process the result set and add each row to the soldItems list
            while (rs.next()) {
                String[] row = new String[6];
                row[0] = rs.getString("QUOTE_ID");
                row[1] = rs.getString("CLIENT_NAME");
                row[2] = rs.getString("QUOTE_DATE");
                row[3] = rs.getString("PART_NAME");
                row[4] = rs.getString("QUANTITY");
                row[5] = rs.getString("TOTAL");
                soldItems.add(row);
            }
        }
        
        return soldItems;
    }
}

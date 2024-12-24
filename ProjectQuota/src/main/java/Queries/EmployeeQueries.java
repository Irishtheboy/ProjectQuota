package Queries;

import DatabaseConnection.DBConnection;
import java.sql.*;

public class EmployeeQueries {

    public static boolean addEmployee(String firstName, String lastName, String username, String password, String email, String role) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();

            // SQL query to insert a new employee
            String sql = "INSERT INTO EMPLOYEES (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, EMAIL, ROLE) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            // Set parameters
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, username);
            ps.setString(4, password); // Store a hashed password for security
            ps.setString(5, email);
            ps.setString(6, role);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;  // Return true if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Verify employee login
    public static boolean verifyLogin(String username, String password) {
        String sql = "SELECT * FROM Employees WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection(); // Using your DBConnection class
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Returns true if a matching user is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

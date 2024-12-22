package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseConnection {

    public static Connection connectToSupabase() {
        // Correct JDBC connection details
        String url = "jdbc:postgresql://db.hdzdmjpjmltelbnuvtpo.supabase.co:5432/postgres";

        String user = "irishtheboy";
        String password = "DxxTXsK1gpRGM8N6";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Supabase database.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return null;
    }
}

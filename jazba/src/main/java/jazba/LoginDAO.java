package jazba;

import java.sql.*;

public class LoginDAO {

    // Database connection parameters (you can move this to a DatabaseHelper class if needed)
    private static final String DB_NAME = "JazbaDB";

    private static final String URL = "jdbc:mysql://localhost:3306/"+DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "2cool4skool";

    // Method to validate user login
    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM member WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // If a record is found, login is successful
            if (rs.next()) {
                return true; // User found with matching username and password
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User not found or query failed
    }
}

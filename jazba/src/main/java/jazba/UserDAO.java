package jazba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2cool4skool";

public static int getUserIDByEmailOrUsername(String emailOrUsername) {
    String query = "SELECT userID FROM memberID WHERE email = ? OR username = ?";
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
         PreparedStatement stmt = connection.prepareStatement(query)) {
        
        // Set the parameter for email or username
        stmt.setString(1, emailOrUsername);
        // Set the parameter for email or username
        stmt.setString(1, emailOrUsername);
        stmt.setString(2, emailOrUsername);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("userID");  // Return the userID
            } else {
                return -1;  // Return -1 if no user found
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return -1;  // Return -1 in case of error
    }
}
}
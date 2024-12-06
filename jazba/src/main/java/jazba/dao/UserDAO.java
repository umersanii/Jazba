package jazba.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jazba.models.Notification;

public class UserDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2cool4skool";

    public int getUserIDByEmailOrUsername(String emailOrUsername) {
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
    
    // Fetch the latest 3 notifications from the database
    public List<Notification> getLatestNotifications(int memberId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT type, message, timestamp FROM notification WHERE memberID = ? ORDER BY timestamp DESC LIMIT 3";
    
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                String type = rs.getString("type");        // Assuming "type" exists in the table
                String message = rs.getString("message");
                String timestamp = rs.getString("timestamp");
    
                // Create a new Notification object and add it to the list
                Notification notification = new Notification(type, message, timestamp);
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }
    
    }


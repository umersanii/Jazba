package jazba;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminBroadcastController {

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView<String> broadcastListView;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tabodi123*";

    @FXML
    private void broadcastMessage() {
        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            statusLabel.setText("Status: Message cannot be empty.");
            return;
        }

        try {
            // Insert the message into the Notification table for all users
            sendMessageToAllUsers(message);

            // Update the ListView with the new message
            broadcastListView.getItems().add(message);

            // Clear the TextArea
            messageArea.clear();

            statusLabel.setText("Status: Message broadcasted successfully!");
        } catch (Exception ex) {
            statusLabel.setText("Status: Failed to send the message.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clearMessage() {
        messageArea.clear();
        statusLabel.setText("Status: Message cleared.");
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) broadcastListView.getScene().getWindow();
        stage.close();
    }

    private void sendMessageToAllUsers(String message) throws Exception {
        Connection connection = null;
        PreparedStatement fetchUsersStmt = null;
        PreparedStatement insertNotificationStmt = null;

        try {
            // Establish the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Fetch all user IDs from the Member table
            String fetchUsersSQL = "SELECT id FROM Member";
            fetchUsersStmt = connection.prepareStatement(fetchUsersSQL);
            ResultSet resultSet = fetchUsersStmt.executeQuery();

            // Insert a notification for each user
            String insertNotificationSQL = "INSERT INTO Notification (type, message, timestamp, status, memberID) VALUES (?, ?, ?, ?, ?)";
            insertNotificationStmt = connection.prepareStatement(insertNotificationSQL);

            while (resultSet.next()) {
                int memberId = resultSet.getInt("id");

                insertNotificationStmt.setString(1, "Broadcast"); // Type: Broadcast
                insertNotificationStmt.setString(2, message); // Message
                insertNotificationStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // Timestamp
                insertNotificationStmt.setString(4, "Unread"); // Status: Unread
                insertNotificationStmt.setInt(5, memberId); // Member ID
                insertNotificationStmt.executeUpdate();
            }
        } finally {
            // Close all database resources
            if (fetchUsersStmt != null) fetchUsersStmt.close();
            if (insertNotificationStmt != null) insertNotificationStmt.close();
            if (connection != null) connection.close();
        }
    }
}

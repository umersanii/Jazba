package jazba;

import javafx.fxml.FXML;
import javafx.scene.control.*;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;

public class AdminBroadcastController {

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView<String> broadcastListView;

    @FXML
    private void broadcastMessage() {
        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            statusLabel.setText("Status: Message cannot be empty.");
            return;
        }

        try {
            // Send the message to the database
          //  sendMessageToDatabase(message);

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

    // private void sendMessageToDatabase(String message) throws Exception {
    //     // Placeholder database logic
    //     // Assuming a table `broadcasts (id INT, message TEXT, timestamp TIMESTAMP)`
    //     String url = "jdbc:mysql://localhost:3306/gym_app";
    //     String user = "root";
    //     String password = "password";

    //     Connection connection = DriverManager.getConnection(url, user, password);
    //     String sql = "INSERT INTO broadcasts (message) VALUES (?)";

    //     PreparedStatement preparedStatement = connection.prepareStatement(sql);
    //     preparedStatement.setString(1, message);
    //     preparedStatement.executeUpdate();

    //     preparedStatement.close();
    //     connection.close();
    // }
}
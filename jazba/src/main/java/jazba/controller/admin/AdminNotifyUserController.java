package jazba.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminNotifyUserController {

    @FXML
    private ListView<String> userListView;

    @FXML
    private TextArea messageArea;

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    private ObservableList<String> allUsers;

    @FXML
    private Button applyFilterButton;

    @FXML
    public void initialize() {
        // Initialize the list of users by fetching from the database
        fetchUsersFromDatabase();

        // Allow multiple selection
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Populate filter choice box
        ObservableList<String> filterOptions = FXCollections.observableArrayList(
            "All Users", "Active", "InActive"
        );
        filterChoiceBox.setItems(filterOptions);

        // Default filter is "All Users"
        filterChoiceBox.setValue("All Users");

        // Set button actions
        applyFilterButton.setOnAction(event -> applyFilter());
    }

    private void fetchUsersFromDatabase() {
        allUsers = FXCollections.observableArrayList();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/JazbaDB";
        String user = "root";
        String password = "2cool4skool";

        String sql = "SELECT id, username, membership_status FROM Member";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String membershipStatus = resultSet.getString("membership_status");

                // Add user information to the list
                allUsers.add(String.format("%s (ID: %d, %s)", username, id, membershipStatus));
            }

            userListView.setItems(allUsers);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Failed to fetch users from the database: " + e.getMessage());
        }
    }

    @FXML
    private void applyFilter() {
        String selectedFilter = filterChoiceBox.getValue();
        if (selectedFilter == null || selectedFilter.equals("All Users")) {
            userListView.setItems(allUsers);
        } else {
            // Apply filtering based on membership status
            ObservableList<String> filteredUsers = FXCollections.observableArrayList();
            for (String user : allUsers) {
                if (selectedFilter.equals("Active") && user.contains("Active")) {
                    filteredUsers.add(user);
                } else if (selectedFilter.equals("InActive") && user.contains("InActive")) {
                    filteredUsers.add(user);
                }
            }
            userListView.setItems(filteredUsers);
        }
    }

    @FXML
    private void sendMessage() {
        ObservableList<String> selectedUsers = userListView.getSelectionModel().getSelectedItems();
        String message = messageArea.getText().trim();

        if (selectedUsers.isEmpty() || message.isEmpty()) {
            showErrorAlert("Please select at least one user and type a message.");
            return;
        }

        // Send messages to the database
        try {
            sendMessagesToDatabase(selectedUsers, message);

            // Clear input fields
            messageArea.clear();
            userListView.getSelectionModel().clearSelection();

            showInfoAlert("Message sent successfully!");
        } catch (Exception ex) {
            showErrorAlert("Failed to send messages: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void sendMessagesToDatabase(ObservableList<String> selectedUsers, String message) throws Exception {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/JazbaDB";
        String user = "root";
        String password = "2cool4skool";

        String sql = "INSERT INTO Notification (type, message, timestamp, status, memberID) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (String userEntry : selectedUsers) {
                int memberId = extractMemberIdFromUserEntry(userEntry);

                preparedStatement.setString(1, "Private");
                preparedStatement.setString(2, message);
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setString(4, "Unread");
                preparedStatement.setInt(5, memberId);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    private int extractMemberIdFromUserEntry(String userEntry) {
        // Extract the member ID from the user entry string
        String[] parts = userEntry.split(",");
        String idPart = parts[0].substring(parts[0].indexOf("(ID: ") + 5).trim();
        return Integer.parseInt(idPart);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void cancel() {
        // Close the Notify User window
        Stage stage = (Stage) userListView.getScene().getWindow();
        stage.close();
    }
}

package jazba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AdminNotifyUserController {

    @FXML
    private ListView<String> userListView;

    @FXML
    private TextArea messageArea;

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    private ObservableList<String> allUsers;

    @FXML
    public void initialize() {
        // Populate the initial list of users
        allUsers = FXCollections.observableArrayList(
            "User1 (Premium)", "User2 (Basic)", "User3 (Premium)", "User4 (Basic)"
        );
        userListView.setItems(allUsers);

        // Default filter is "All Users"
        filterChoiceBox.setValue("All Users");
    }

    @FXML
    private void applyFilter() {
        String selectedFilter = filterChoiceBox.getValue();
        if (selectedFilter == null || selectedFilter.equals("All Users")) {
            userListView.setItems(allUsers);
        } else {
            // Apply filtering based on user type
            List<String> filteredUsers = new ArrayList<>();
            for (String user : allUsers) {
                if (selectedFilter.equals("Premium Users") && user.contains("Premium")) {
                    filteredUsers.add(user);
                } else if (selectedFilter.equals("Basic Users") && user.contains("Basic")) {
                    filteredUsers.add(user);
                }
            }
            userListView.setItems(FXCollections.observableArrayList(filteredUsers));
        }
    }

    @FXML
    private void sendMessage() {
        ObservableList<String> selectedUsers = userListView.getSelectionModel().getSelectedItems();
        String message = messageArea.getText().trim();

        if (selectedUsers.isEmpty() || message.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select at least one user and type a message.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Simulate sending the message (e.g., to a database or API)
        for (String user : selectedUsers) {
            System.out.println("Message sent to " + user + ": " + message);
        }

        // Clear input fields
        messageArea.clear();
        userListView.getSelectionModel().clearSelection();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Message sent successfully!", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void cancel() {
        // Close the Notify User window
        Stage stage = (Stage) userListView.getScene().getWindow();
        stage.close();
    }
}


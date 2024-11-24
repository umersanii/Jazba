package jazba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

public class UserManagementController {

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> membershipColumn;

    @FXML
    private TextArea userDetailsArea;

    private ObservableList<User> allUsers;

    @FXML
    public void initialize() {
        // Mock user data
        allUsers = FXCollections.observableArrayList(
            new User("1", "Alice", "Premium"),
            new User("2", "Bob", "Basic"),
            new User("3", "Charlie", "Premium"),
            new User("4", "Diana", "Basic")
        );

        // Set up TableView columns
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        membershipColumn.setCellValueFactory(data -> data.getValue().membershipProperty());

        // Populate TableView
        userTableView.setItems(allUsers);

        // Add options to filterChoiceBox
        filterChoiceBox.setItems(FXCollections.observableArrayList("All Users", "Premium Users", "Basic Users"));
        filterChoiceBox.setValue("All Users");

        // Add listener to display user details when a row is selected
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayUserDetails(newSelection);
            }
        });
    }

    @FXML
    private void applyFilter() {
        String selectedFilter = filterChoiceBox.getValue();
        if ("All Users".equals(selectedFilter)) {
            userTableView.setItems(allUsers);
        } else {
            List<User> filteredUsers = new ArrayList<>();
            for (User user : allUsers) {
                if (selectedFilter.equals("Premium Users") && "Premium".equals(user.getMembership())) {
                    filteredUsers.add(user);
                } else if (selectedFilter.equals("Basic Users") && "Basic".equals(user.getMembership())) {
                    filteredUsers.add(user);
                }
            }
            userTableView.setItems(FXCollections.observableArrayList(filteredUsers));
        }
    }

    private void displayUserDetails(User user) {
        // Mock user details
        String details = String.format("User ID: %s\nName: %s\nMembership: %s\nWorkout Plans: Sample Plan\nAchievements: 5 Badges",
                user.getId(), user.getName(), user.getMembership());
        userDetailsArea.setText(details);
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) filterChoiceBox.getScene().getWindow();
        stage.close();
    }
}


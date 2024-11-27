package jazba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
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
        // Initialize the database connection and populate the user list
        populateUsersFromDatabase();

        // Set up TableView columns
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        membershipColumn.setCellValueFactory(data -> data.getValue().membershipProperty());

        // Populate TableView
        userTableView.setItems(allUsers);

        // Add options to filterChoiceBox
        filterChoiceBox.setItems(FXCollections.observableArrayList("All Users", "Active", "InActive"));
        filterChoiceBox.setValue("All Users");

        // Add listener to display user details when a row is selected
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayUserDetails(newSelection);
            }
        });
    }

    private void populateUsersFromDatabase() {
        allUsers = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jazbaDB", "root", "Tabodi123*");
             Statement statement = connection.createStatement()) {

            String query = "SELECT id, username, membership_status FROM Member";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                allUsers.add(new User(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        
                        resultSet.getString("membership_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void applyFilter() {
        String selectedFilter = filterChoiceBox.getValue();
        if ("All Users".equals(selectedFilter)) {
            userTableView.setItems(allUsers);
        } else {
            List<User> filteredUsers = new ArrayList<>();
            for (User user : allUsers) {
                if (selectedFilter.equals("Active Users") && "Active".equals(user.getMembership())) {
                    filteredUsers.add(user);
                } else if (selectedFilter.equals("InActive Users") && "InActive".equals(user.getMembership())) {
                    filteredUsers.add(user);
                }
            }
            userTableView.setItems(FXCollections.observableArrayList(filteredUsers));
        }
    }

    private void displayUserDetails(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jazbaDB", "root", "Tabodi123*");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Profile WHERE memberID = ?")) {

            preparedStatement.setInt(1, Integer.parseInt(user.getId()));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String details = String.format("User ID: %s\nName: %s\nMembership: %s\nHeight: %d\nWeight: %d\nAge: %d\nFitness Level: %s",
                        user.getId(),
                        user.getName(),
                        user.getMembership(),
                        resultSet.getInt("height"),
                        resultSet.getInt("weight"),
                        resultSet.getInt("age"),
                        resultSet.getString("fitnessLevel")
                );

                showPopup(details);
            } else {
                showPopup("No profile data available for the selected user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showPopup(String details) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfilePopup.fxml"));
            Parent root = loader.load();

            ProfilePopupController controller = loader.getController();
            controller.setProfileDetails(details);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User Profile Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) filterChoiceBox.getScene().getWindow();
        stage.close();
    }
}

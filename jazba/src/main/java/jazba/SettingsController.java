package jazba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    @FXML
    private TextField usernameField;

    private String currentUsername = "Admin"; // Default username

    @FXML
    public void initialize() {
        // Populate theme choices
        ObservableList<String> themes = FXCollections.observableArrayList("Light Mode", "Dark Mode", "Blue Theme", "Custom Theme");
        themeChoiceBox.setItems(themes);
        themeChoiceBox.setValue("Dark Mode"); // Default value
    }

    @FXML
    private void updateProfile() {
        String newUsername = usernameField.getText().trim();
        if (!newUsername.isEmpty()) {
            currentUsername = newUsername;
            System.out.println("Username updated to: " + currentUsername);
            usernameField.clear();
        } else {
            System.out.println("Please enter a valid username.");
        }
    }

    @FXML
    private void resetSettings() {
        // Reset to default settings
        themeChoiceBox.setValue("Dark Mode");
        currentUsername = "Admin";
        System.out.println("Settings have been reset to defaults.");
    }

    @FXML
    private void goBack() {
        // Close the current settings window
        Stage stage = (Stage) themeChoiceBox.getScene().getWindow();
        stage.close();
    }
}


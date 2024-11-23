package application.controllers;

import application.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField fitnessLevelField;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        // Handle the Register button action
        submitButton.setOnAction(event -> handleRegister());

        // Handle the Back button action
        backButton.setOnAction(event -> handleBack());
    }

    private void handleRegister() {
        // Gather input values
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String height = heightField.getText();
        String weight = weightField.getText();
        String age = ageField.getText();
        String fitnessLevel = fitnessLevelField.getText();

        // Validate inputs
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || age.isEmpty() || fitnessLevel.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        try {
            int heightValue = Integer.parseInt(height);
            int weightValue = Integer.parseInt(weight);
            int ageValue = Integer.parseInt(age);

            // Registration logic (replace with actual backend logic as needed)
            System.out.println("User Registered:");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Height: " + heightValue + " cm");
            System.out.println("Weight: " + weightValue + " kg");
            System.out.println("Age: " + ageValue);
            System.out.println("Fitness Level: " + fitnessLevel);

            showAlert("Success", "Registration completed successfully!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Height, weight, and age must be numeric values!");
        }
    }

    private void handleBack() {
        // Logic for navigating back to the previous scene
        System.out.println("Back button clicked. Navigating to the previous scene...");
        SceneManager.switchScene("/application/fxml/Login.fxml");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

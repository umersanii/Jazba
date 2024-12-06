package jazba.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import jazba.models.SceneManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private Button registerButton;

    @FXML
    private Button backButton;

    private RegistrationService registrationService;

    @FXML
    public void initialize() {
        registrationService = new RegistrationService();
        registerButton.setOnAction(event -> handleRegister());
        backButton.setOnAction(event -> handleBack());
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String height = heightField.getText();
        String weight = weightField.getText();
        String age = ageField.getText();
        String fitnessLevel = fitnessLevelField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || age.isEmpty() || fitnessLevel.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        try {
            int heightValue = Integer.parseInt(height);
            int weightValue = Integer.parseInt(weight);
            int ageValue = Integer.parseInt(age);

            // Use Command Pattern to execute registration
            RegisterUserCommand registerCommand = new RegisterUserCommand(
                registrationService,
                username,
                email,
                hashPassword(password),
                heightValue,
                weightValue,
                ageValue,
                fitnessLevel
            );

            if (registerCommand.execute()) {
                showAlert("Success", "Registration completed successfully!");
            } else {
                showAlert("Error", "Registration failed.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Height, weight, and age must be numeric values!");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleBack() {
        SceneManager.switchScene("Login.fxml");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
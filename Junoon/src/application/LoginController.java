package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        // Handle the Login button action
        loginButton.setOnAction(event -> handleLogin());

        // Handle the Register button action
        registerButton.setOnAction(event -> handleRegister());
    }

    private void handleLogin() {
        // Gather input values
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password are required!");
            return;
        }

        // Example Login Logic (replace with actual validation logic, e.g., database check)
        if (username.equals("testuser") && password.equals("password")) {
            showAlert("Success", "Login successful!");
        } else {
            showAlert("Error", "Invalid username or password!");
        }
    }

    private void handleRegister() {
        // Logic for navigating to the registration scene
        System.out.println("Navigating to the Registration page...");
        // Add scene-switching logic here if needed
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

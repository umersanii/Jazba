package jazba;

import jazba.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    public void addHoverEffect(MouseEvent event) {
        // Add your hover effect logic here
        System.out.println("Hover effect triggered");
    }
    

    @FXML
    public void showUserMenu(MouseEvent event) {
        System.out.println("User menu clicked!");
        // Logic for displaying the user menu
    }
    
    @FXML
    public void showNotifications(MouseEvent event) {
        // Your logic for showing notifications
        System.out.println("Notifications clicked!");
    }
    

    @FXML
    public void initialize() {
        System.out.println("Initialize method called");
        loginButton.setOnAction(event -> handleLogin());
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
        SceneManager.switchScene("Registration.fxml"); // Adjust to your actual registration FXML file name
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


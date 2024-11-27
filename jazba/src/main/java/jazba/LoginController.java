package jazba;

import jazba.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASSWORD = "Tabodi123*"; // Replace with your DB password

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password are required!");
            return;
        }

        // Hash the password entered by the user
        String hashedPassword = hashPassword(password);

        // Check credentials against the database
        if (validateCredentials(username, hashedPassword)) {
            showAlert("Success", "Login successful!");
            SceneManager.switchScene("MainScene.fxml"); // Navigate to the main scene
        } else {
            showAlert("Error", "Invalid username or password!");
        }
    }

    private boolean validateCredentials(String username, String hashedPassword) {
        String query = "SELECT * FROM Member WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a matching record is found

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database connection failed!");
            return false;
        }
    }

    private void handleRegister() {
        SceneManager.switchScene("Registration.fxml");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String hashPassword(String password) {
        try {
            // SHA-256 hashing
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            // Convert byte array to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Password hashing failed!");
            return null;
        }
    }
}

package jazba.controller.misc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jazba.dao.AchievementDAO;
import jazba.models.SceneManager;

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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2cool4skool";

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

        // Check credentials against the database
        if (validateCredentials(username, password) == 1) {
            showAlert("Success", "Login successful!");
            AchievementDAO achievementDAO = new AchievementDAO();
            achievementDAO.checkAndUnlockAchievements(1);
            SceneManager.switchScene("/jazba/view/user/MainScene.fxml"); // Navigate to the main scene
        } else if (validateCredentials(username, password) == 2) {
            showAlert("Success", "Login successful!");
            SceneManager.switchScene("/jazba/view/admin/admin.fxml"); // Navigate to the admin dashboard
        } else {
            showAlert("Error", "Invalid username or password!");
        }
    }

    private int validateCredentials(String username, String hashedPassword) {
        String queryMember = "SELECT * FROM Member WHERE username = ? AND password = ?";
        String queryAdmin = "SELECT * FROM Admin WHERE username = ? AND password = ?";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatementMember = connection.prepareStatement(queryMember);
             PreparedStatement preparedStatementAdmin = connection.prepareStatement(queryAdmin)) {
    
            // Check in Member table
            preparedStatementMember.setString(1, username);
            preparedStatementMember.setString(2,hashPassword(hashedPassword));
            ResultSet resultSetMember = preparedStatementMember.executeQuery();
    
            if (resultSetMember.next()) {
                return 1; // Member found, show user dashboard
            }
    
            // Check in Admin table
            preparedStatementAdmin.setString(1, username);
            preparedStatementAdmin.setString(2, hashedPassword);
            ResultSet resultSetAdmin = preparedStatementAdmin.executeQuery();
    
            if (resultSetAdmin.next()) {
                return 2; // Admin found, show admin dashboard
            }
    
            return -1; // No match found in either table
    
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database connection failed!");
            return -1;
        }
    }
    

    private void handleRegister() {
        SceneManager.switchScene("/jazba/view/Registration.fxml");
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

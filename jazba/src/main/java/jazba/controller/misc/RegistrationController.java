package jazba.controller.misc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import jazba.dao.AchievementDAO;
import jazba.dao.RegistrationDAO;
import jazba.models.SceneManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationController {

    private RegistrationDAO registrationDAO = new RegistrationDAO();

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
    private TextField name;

    @FXML
    private Button backButton;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USER = "root"; // Your DB username
    private static final String DB_PASSWORD = "2cool4skool"; // Your DB password

    @FXML
    public void initialize() {
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
        String names = name.getText();

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

            if (isUsernameTaken(username)) {
                showAlert("Error", "Username already exists. Please choose another one.");
                return;
            }

            // Hash password
            String hashedPassword = hashPassword(password);

            // Register the user in the Member table
            int memberId = registerMember(username, email, hashedPassword);

            if (memberId > 0) {
                // Register profile data in the Profile table
                boolean profileAdded = registerProfile(heightValue, weightValue, ageValue, fitnessLevel, memberId, names);

                if (profileAdded) {
                    showAlert("Success", "Registration completed successfully!");
                AchievementDAO achievementDAO = new AchievementDAO();
                achievementDAO.initializeAchievementsForUser(memberId); // Initialize achievements for the new user
                } else {
                    showAlert("Error", "Failed to add profile information.");
                }
            } else {
                showAlert("Error", "Failed to register user.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Height, weight, and age must be numeric values!");
        }
    }

    private boolean isUsernameTaken(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM Member WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Returns true if username exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // Returns the hashed password as a hex string
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int registerMember(String username, String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Member (username, email, password, membership_status, registration_date) "
                         + "VALUES (?, ?, ?, 'Active', CURDATE())";
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Returns the member ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if registration fails
    }

    private boolean registerProfile(int height, int weight, int age, String fitnessLevel, int memberId, String name) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Profile (height, weight, age, fitnessLevel, memberID, name) "
                         + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, height);
            preparedStatement.setInt(2, weight);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, fitnessLevel);
            preparedStatement.setInt(5, memberId);
            preparedStatement.setString(6, name);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Return true if profile is added successfully
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void handleBack() {
        SceneManager.switchScene("/jazba/view/Login.fxml");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

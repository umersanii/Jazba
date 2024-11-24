package jazba;

import jazba.SceneManager;
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

    private RegistrationDAO registrationDAO;

    @FXML
    public void initialize() {
        registrationDAO = new RegistrationDAO(); // Initialize the DAO class

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

            // Call DAO to register the user
            boolean isRegistered = registrationDAO.registerUser(username, email, password, heightValue, weightValue, ageValue, fitnessLevel);

            if (isRegistered) {
                showAlert("Success", "Registration completed successfully!");
                // Navigate to the login screen or another page
                SceneManager.switchScene("Login.fxml"); // Adjust to your actual login FXML file name
            } else {
                showAlert("Error", "Registration failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Height, weight, and age must be numeric values!");
        }
    }

    private void handleBack() {
        // Logic for navigating back to the login scene
        System.out.println("Back button clicked. Navigating to the login scene...");
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

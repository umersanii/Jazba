package jazba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class MainSceneController {

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
    private void initialize() {
        // Ensure the button is properly loaded from FXML
        if (loginButton != null) {
            loginButton.setOnAction(event -> {
                System.out.println("Hover effect triggered");
            });
        } else {
            System.out.println("loginButton is null!");
        }
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
    private void navigateToStatsPage(ActionEvent event) {
        try {
            // Load the StatsPage FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StatsPage.fxml"));
            AnchorPane statsPage = loader.load();

            // Create a new scene with the loaded FXML
            Scene statsScene = new Scene(statsPage);

            // Get the current stage (window) and set the new scene
            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();
            primaryStage.setScene(statsScene);
            primaryStage.show();
        } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        


        // Method that is called when the Create Workout button is clicked
    @FXML
    private void handleCreateWorkout(ActionEvent event) {
        // Logic for creating a workout (this could be opening another scene, showing a dialog, etc.)
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Create Workout");
        alert.setHeaderText(null);
        alert.setContentText("Create Workout Button Clicked!");
        alert.showAndWait();
    }
    
    
}

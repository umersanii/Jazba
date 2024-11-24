package jazba;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class LogWorkoutController {

    @FXML
    private Button overviewButton;
    
    @FXML
    private Button logsButton;
    
    @FXML
    private Button achievementsButton;
    
    @FXML
    private Button statsButton;
    
    @FXML
    private Label notificationBell;
    
    @FXML
    private Label userLabel;
    
    @FXML
    private ListView<?> listView; // ListView for displaying workout logs (you can specify the type of objects it holds)

    @FXML
    public void initialize() {
        // Initialization code (if needed)
        setUpButtonActions();
    }

    private void setUpButtonActions() {
        overviewButton.setOnAction(event -> handleOverview());
        logsButton.setOnAction(event -> handleLogs());
        achievementsButton.setOnAction(event -> handleAchievements());
        statsButton.setOnAction(event -> handleStats());
    }

    private void handleOverview() {
        System.out.println("Overview Button Clicked");
        // Navigate to Overview screen (e.g., switch scenes or load new content)
    }

    private void handleLogs() {
        System.out.println("Logs Button Clicked");
        // Navigate to Logs screen
    }

    private void handleAchievements() {
        System.out.println("Achievements Button Clicked");
        // Navigate to Achievements screen
    }

    private void handleStats() {
        System.out.println("Stats Button Clicked");
        // Navigate to Stats screen
    }

    // Optional: Methods for handling the notification bell or user label if needed
    @FXML
    private void handleNotificationBell() {
        System.out.println("Notification Bell Clicked");
        // Handle notifications
    }

    @FXML
    private void handleUserLabel() {
        System.out.println("User Label Clicked");
        // Handle user profile
    }

    // Optionally, you can add methods to populate the ListView with workout data
    public void populateListView() {
        // Example: listView.getItems().addAll(...);
    }
}

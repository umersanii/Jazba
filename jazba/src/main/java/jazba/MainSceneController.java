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
import javafx.scene.control.Tooltip;

public class MainSceneController {

    // FXML buttons linked to the UI
    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    // FXML variables linked to the UI
    @FXML
    private Node badgeTwelfth;

    @FXML
    private Node badgeEleventh;

    @FXML
    private Node badgeTenth;

    @FXML
    private Node badgeNinth;

    @FXML
    private Node badgeEighth;

    @FXML
    private Node badgeSeventh;

    @FXML
    private Node badgeSixth;

    @FXML
    private Node badgeFifth;

    @FXML
    private Node badgeFourth;

    @FXML
    private Node badgeThird;

    @FXML
    private Node badgeSecond;

    @FXML
    private Node badgeFirst;

    // Hover effect for buttons or UI elements
    @FXML
    public void addHoverEffect(MouseEvent event) {
        // Add your hover effect logic here
        System.out.println("Hover effect triggered");
    }

    @FXML
    private void handleLogWorkout(ActionEvent event) {
        // Alert showing that the button was clicked
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Log Workout");
        alert.setHeaderText(null);
        alert.setContentText("Log Workout Button Clicked!");
        alert.showAndWait();
    }

    @FXML
    private void navigateToAchievements(ActionEvent event) {
        // Alert showing that the button was clicked
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Log Workout");
        alert.setHeaderText(null);
        alert.setContentText("Log Workout Button Clicked!");
        alert.showAndWait();
    }
    // Initial setup for buttons and event handlers
    @FXML
    private void initialize() {
        // Ensure the login button is properly loaded from FXML
        Tooltip.install(badgeFirst, new Tooltip("First Steps\nComplete your first workout. A journey of a thousand miles begins with a single step!"));
        Tooltip.install(badgeSecond, new Tooltip("Consistency is Key\nLog workouts for 7 consecutive days. You're on a roll!"));
        Tooltip.install(badgeThird, new Tooltip("Beast Mode\nLift a total of 10,000 lbs across all workouts. Strength unleashed!"));
        Tooltip.install(badgeFourth, new Tooltip("Cardio King/Queen\nRun or bike a total of 50 kilometers. Keep the heart racing!"));
        Tooltip.install(badgeFifth, new Tooltip("Iron Addict\nBench press your body weight. True dedication!"));
        Tooltip.install(badgeSixth, new Tooltip("Marathon Mentality\nWork out for 30 consecutive days. A champion in the making!"));
        Tooltip.install(badgeSeventh, new Tooltip("Variety is the Spice of Fitness\nTry 10 different exercises. Explore and conquer!"));
        Tooltip.install(badgeEighth, new Tooltip("Milestone Master\nLog 100 workouts. Perseverance pays off!"));
        Tooltip.install(badgeNinth, new Tooltip("One for the Records\nSet a new personal best in any exercise. Keep breaking barriers!"));
        Tooltip.install(badgeTenth, new Tooltip("Fitness Guru\nCoach or inspire a friend to join Jazba. Your influence grows!"));
        Tooltip.install(badgeEleventh, new Tooltip("Hall of Fame\nAchieve all achievements. The ultimate fitness legend!"));
        Tooltip.install(badgeTwelfth, new Tooltip("Calorie Crusher\nBurn 5,000 calories in workouts. A true shredder!"));

        if (loginButton != null) {
            // Setting an action for the login button
            loginButton.setOnAction(event -> {
                System.out.println("Login button clicked!");
                // Additional logic can be added here
            });
        } else {
            System.out.println("loginButton is null!");
        }
        
        // You can add more button initializations or actions here if needed
    }

    // Show the user menu when clicked
    @FXML
    public void showUserMenu(MouseEvent event) {
        System.out.println("User menu clicked!");
        // Logic for displaying the user menu can go here
    }

    // Show notifications when clicked
    @FXML
    public void showNotifications(MouseEvent event) {
        System.out.println("Notifications clicked!");
        // Logic for displaying notifications can go here
    }

    // Navigate to the Stats page when called
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

    // Logic for creating a workout, triggered by button click
    @FXML
    private void handleCreateWorkout(ActionEvent event) {
        // Alert showing that the button was clicked
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Create Workout");
        alert.setHeaderText(null);
        alert.setContentText("Create Workout Button Clicked!");
        alert.showAndWait();
    }
}

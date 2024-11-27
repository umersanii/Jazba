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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class MainSceneController {

    // FXML buttons linked to the UI
    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    // FXML variables linked to the UI
    @FXML
    private Node badge12;

    @FXML
    private Node badge11;

    @FXML
    private Node badge10;

    @FXML
    private Node badge9;

    @FXML
    private Node badge8;

    @FXML
    private Node badge7;

    @FXML
    private Node badge6;

    @FXML
    private Node badge5;

    @FXML
    private Node badge4;

    @FXML
    private Node badge3;

    @FXML
    private Node badge2;

    @FXML
    private Node badge1;

    private AchievementDAO achievementDAO; // Declare AchievementDAO

    // Hover effect for buttons or UI elements
    @FXML
    public void handleImageHover(MouseEvent event) {
        // Get the source of the mouse event (the node that was hovered)
        Node source = (Node) event.getSource();
        
        // Create a new tooltip for each badge
        Tooltip tooltip = new Tooltip();
        
        // Install the tooltip to the hovered node (badge)
        Tooltip.install(source, tooltip);
        tooltip.setStyle("-fx-background-color: black; -fx-text-fill: yellow; -fx-font-size: 14px; -fx-font-family: 'Arial';");

        // Set the tooltip properties based on the hovered badge
        switch (source.getId()) {
            case "badge1":
                tooltip.setText("First Steps");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Complete your first workout!"));
                break;
            case "badge2":
                tooltip.setText("Consistency is Key");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Log workouts for 7 consecutive days. You're on a roll!"));
                break;
            case "badge3":
                tooltip.setText("Beast Mode");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Lift a total of 5000 kgs across all workouts. Strength unleashed!"));
                break;
            case "badge4":
                tooltip.setText("Cold Sweat");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Log a total of 1000 reps. Keep the heart reps up!"));
                break;
            case "badge5":
                tooltip.setText("Iron Addict");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Bench press your body weight. True dedication!"));
                break;
            case "badge6":
                tooltip.setText("Marathon Mentality");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Work out for 30 consecutive days. A champion in the making!"));
                break;
            case "badge7":
                tooltip.setText("Variety is the Spice of Fitness");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Try 10 different exercises. Explore and conquer!"));
                break;
            case "badge8":
                tooltip.setText("Milestone Master");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Log 100 workouts. Perseverance pays off!"));
                break;
            case "badge9":
                tooltip.setText("The Beast Within");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Achieve Deadlift of 100kg, Squat of 80kg and Bench Press of 60kg. You're a beast!"));
                break;
            case "badge10":
                tooltip.setText("Powerhouse");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Complete a total of 200 sets across all exercises. Power through your workouts!"));
                break;
            case "badge11":
                tooltip.setText("Milestone Master");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Log 100 workouts. Perseverance pays off!"));
                break;
            case "badge12":
                tooltip.setText("Hall of Fame");
                tooltip.setContentDisplay(ContentDisplay.BOTTOM);
                tooltip.setGraphic(new Label("Achieve all achievements. The ultimate fitness legend!"));
                break;
            default:
                tooltip.setText("Achievement unlocked!");
                tooltip.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-size: 14px;");
                break;
        }

        // Show the tooltip when the mouse is entered and hide it when mouse exits
        source.setOnMouseEntered(e -> {
            tooltip.show(source, e.getScreenX() + 10, e.getScreenY() + 10);
        });

        source.setOnMouseExited(e -> {
            tooltip.hide();
        });
    }

    @FXML
    public void handleImageExit(MouseEvent event) {
        Node source = (Node) event.getSource();
        Tooltip.uninstall(source, null); // Remove tooltip on exit
    }

    // Method to check and update achievement badges based on user progress
    private void updateAchievements(int memberId) {
        try {
            // Initialize AchievementDAO to interact with the database
            achievementDAO = new AchievementDAO();
            ColorAdjust colorAdjust = new ColorAdjust();

            // Fetch achievements for the member
            for (int i = 1; i <= 12; i++) {
                boolean isUnlocked = achievementDAO.isAchievementUnlocked(2, "badge" + i); // Check if achievement is unlocked
                System.out.println("Achievement " + i + " unlocked: " + isUnlocked);
                Node badge = (Node) getClass().getDeclaredField("badge" + i).get(this); // Dynamic badge reference
                
                // Update badge styling based on whether the achievement is unlocked
                if (isUnlocked) {
                    badge.setEffect(null);  // Change background color to golden if not unlocked
                } else {
                    badge.setEffect(colorAdjust);  
                    colorAdjust.setSaturation(-1);  // Makes the image black and white
                      // Change background color to black if unlocked
                     // Change background color to golden if not unlocked
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateBadgeColor(ImageView badge, boolean isUnlocked) {
        ColorAdjust colorAdjust = new ColorAdjust();
        
        if (isUnlocked) {
            colorAdjust.setSaturation(-1);  // Grayscale (black & white)
        } else {
            colorAdjust.setBrightness(1.2); // Simulate golden glow
            colorAdjust.setHue(0.1);         // Slight hue shift for golden effect
        }
        
        badge.setEffect(colorAdjust);
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

    // Initial setup for buttons and event handlers
    @FXML
    private void initialize() {
        // Call the updateAchievements method for a member (use a valid memberID from your session)
        int memberId = 2; // Example memberId, adjust based on your session management
        updateAchievements(memberId);

        if (loginButton != null) {
            // Setting an action for the login button
            loginButton.setOnAction(event -> {
                System.out.println("Login button clicked!");
                // Additional logic can be added here
            });
        } else {
            System.out.println("loginButton is null!");
        }
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

    @FXML
    private void addHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }
}

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class MainSceneController {

    @FXML
    private Button achievementsButton;

    @FXML
    private Button logsButton;

    @FXML
    private Button overviewButton;

    @FXML
    private Button statsButton;

    @FXML
    private Label notificationBell;

    @FXML
    private Label userLabel;

    

    private ContextMenu contextMenu;

    @FXML
    public void initialize() {
        // Set actions for buttons
        overviewButton.setOnAction(event -> showAlert("Overview"));
        logsButton.setOnAction(event -> showAlert("Logs"));
        achievementsButton.setOnAction(event -> showAlert("Achievements"));
        statsButton.setOnAction(event -> showAlert("Stats"));

        // muscleComboBox.getItems().addAll(
        //     "Biceps",
        //     "Triceps",
        //     "Shoulders",
        //     "Chest",
        //     "Back",
        //     "Legs",
        //     "Abs"
        // );

        // Create context menu
    contextMenu = new ContextMenu();
    MenuItem logout = new MenuItem("Logout");
    logout.setOnAction(event -> logout());

    contextMenu.getItems().addAll(logout);
    }

   

    private void showAlert(String sectionName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Button Clicked");
        alert.setHeaderText(null);
        alert.setContentText("You clicked the " + sectionName + " button!");
        alert.showAndWait();
    }
    
@FXML
public void showUserMenu() {
    contextMenu.show(userLabel, Side.BOTTOM, 0, 0);
}

private void logout() {
    System.out.println("Logging out...");
    // Redirect to login screen
    // Use FXMLLoader to load the login scene
}

    public void showNotifications() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Notifications");
    alert.setHeaderText("You have new notifications!");
    alert.setContentText("1. Task completed.\n2. New message received.");
    alert.showAndWait();
}
}

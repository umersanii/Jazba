package application;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

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

   public MainSceneController() {}

   @FXML
   public void initialize() {
      this.overviewButton.setOnAction((event) -> {
         this.showAlert("Overview");
      });
      this.logsButton.setOnAction((event) -> {
         this.showAlert("Logs");
      });
      this.achievementsButton.setOnAction((event) -> {
         this.showAlert("Achievements");
      });
      this.statsButton.setOnAction((event) -> {
         this.showAlert("Stats");
      });
      this.contextMenu = new ContextMenu();
      MenuItem logoutMenuItem = new MenuItem("Logout");
      logoutMenuItem.setOnAction((event) -> {
         this.logout();
      });
      this.contextMenu.getItems().addAll(logoutMenuItem);
   }

   private void showAlert(String buttonName) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Button Clicked");
      alert.setHeaderText(null);
      alert.setContentText("You clicked the " + buttonName + " button!");
      alert.showAndWait();
   }

   @FXML
   public void showUserMenu() {
      this.contextMenu.show(this.userLabel, Side.BOTTOM, 0.0, 0.0);
   }

   private void logout() {
      System.out.println("Logging out...");
   }

   public void showNotifications() {
      Alert notificationAlert = new Alert(Alert.AlertType.INFORMATION);
      notificationAlert.setTitle("Notifications");
      notificationAlert.setHeaderText("You have new notifications!");
      notificationAlert.setContentText("1. Task completed.\n2. New message received.");
      notificationAlert.showAndWait();
   }

   @FXML
   public void addHoverEffect(MouseEvent event) {
      System.out.println("Mouse hover detected on: " + event.getSource());
      // Example: Add a visual effect to the hovered element
      if (event.getSource() instanceof Button) {
         Button button = (Button) event.getSource();
         button.setStyle("-fx-background-color: lightblue;");
      }
   }

   @FXML
public void removeHoverEffect(MouseEvent event) {
    if (event.getSource() instanceof Button) {
       Button button = (Button) event.getSource();
       button.setStyle(""); // Reset to default
    }
}
}

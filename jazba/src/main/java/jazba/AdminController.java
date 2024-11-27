package jazba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button broadcastmessagebutton;

    @FXML
    private Button checktransactionbutton;

    @FXML
    private Button manageusers;

    @FXML
    private Button notifyuserbutton;

    @FXML
    private Button overviewbutton;

    @FXML
    private Button transactionbutton;

    @FXML
    private Button userdetailbutton;

    @FXML
    private void handleBroadcastMessageButton(ActionEvent event) {
        try {
            // Load the AdminBroadcast.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminBroadcast.fxml"));
            Parent root = loader.load();

            // Get the current stage (window)
          // Get the current stage (window)
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

            // Optionally set a title
            stage.setTitle("Broadcast Message");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNotifyUserButton(ActionEvent event) {
    try {
        // Load the AdminNotifyUser.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminNotifyUser.fxml"));
        Parent root = loader.load();

        // Get the current stage (window)
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        // Set window properties
        stage.setTitle("Notify Specific Users");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
private void openUserManagement() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserManagement.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("User Management");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
private void handleSettings() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Settings");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
private void handleFeedback() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Feedback");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@FXML
private void handleSystemLogs() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SystemLogs.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("System Logs");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
private void handleReports() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reports.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("System Logs");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



}



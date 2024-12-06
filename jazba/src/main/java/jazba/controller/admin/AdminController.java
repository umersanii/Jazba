package jazba.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private void handleBroadcastMessageButton(ActionEvent event) {
        loadFXML("/jazba/view/admin/AdminBroadcast.fxml", "Broadcast Message");
    }

    @FXML
    private void handleNotifyUserButton(ActionEvent event) {
        loadFXML("/jazba/view/admin/AdminNotifyUser.fxml", "Notify Specific Users");
    }

    @FXML
    private void openUserManagement() {
        loadFXML("/jazba/view/admin/UserManagement.fxml", "User Management");
    }

    @FXML
    private void handleSettings() {
        loadFXML("/jazba/view/admin/Settings.fxml", "Settings");
    }

    @FXML
    private void handleFeedback() {
        loadFXML("/jazba/view/admin/Feedback.fxml", "Feedback");
    }

    @FXML
    private void handleSystemLogs() {
        loadFXML("/jazba/view/admin/SystemLogs.fxml", "System Logs");
    }

    @FXML
    private void handleReports() {
        loadFXML("/jazba/view/admin/Reports.fxml", "Reports");
    }

    /**
     * Utility method to load an FXML file and display it in a new stage.
     *
     * @param fxmlPath Path to the FXML file.
     * @param title    Title of the stage.
     */
    private void loadFXML(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Set up a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }
}

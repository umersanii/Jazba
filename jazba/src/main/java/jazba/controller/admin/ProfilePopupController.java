package jazba.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfilePopupController {

    @FXML
    private Label profileDetailsLabel;

    /**
     * Sets the profile details to display in the label.
     * @param details The profile details as a string.
     */
    public void setProfileDetails(String details) {
        profileDetailsLabel.setText(details);
    }

    /**
     * Closes the popup window.
     */
    @FXML
    private void closePopup() {
        // Close the current stage (popup window)
        Stage stage = (Stage) profileDetailsLabel.getScene().getWindow();
        stage.close();
    }
}

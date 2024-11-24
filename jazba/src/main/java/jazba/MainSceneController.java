package jazba;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
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
    
    
}

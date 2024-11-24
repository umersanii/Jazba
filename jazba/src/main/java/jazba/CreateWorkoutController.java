package jazba;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.*;

public class CreateWorkoutController {

    @FXML
    private ListView<VBox> exerciseList;  // Main list where exercises will be added

    // Method to add exercise to the main list view
    public void addExerciseToList(String exerciseName, String description, String targetMuscles) {
        // Create a new VBox for the exercise card
        VBox exerciseCard = new VBox(10);
        exerciseCard.setStyle("-fx-background-color: #333; -fx-border-radius: 10; -fx-padding: 15; -fx-spacing: 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5);");

        // Exercise title
        Label titleLabel = new Label(exerciseName);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        // Exercise description
        Label descriptionLabel = new Label(description);
        descriptionLabel.setStyle("-fx-text-fill: #bbb; -fx-font-size: 14px;");

        // Target muscles label
        Label targetMusclesLabel = new Label("Target Muscles: " + targetMuscles);
        targetMusclesLabel.setStyle("-fx-text-fill: #bbb; -fx-font-size: 14px;");

        // Add the labels to the exercise card
        exerciseCard.getChildren().addAll(titleLabel, descriptionLabel, targetMusclesLabel);

        // Add the exercise card to the ListView
        exerciseList.getItems().add(exerciseCard);
    }

    public void openExerciseSelectionPage() {
        // Open the ExerciseSelection page and pass the CreateWorkoutController to it
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExerciseSelection.fxml"));
            Parent root = loader.load();

            // Get the controller from the loaded FXML file
            ExerciseSelectionController controller = loader.getController();

            // Pass this CreateWorkoutController to the ExerciseSelectionController
            controller.setCreateWorkoutController(this);

            // Set the new scene with ExerciseSelection page
            Stage stage = (Stage) exerciseList.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initialize() {
        // Initially open the Exercise Selection page
        openExerciseSelectionPage();
    }
}

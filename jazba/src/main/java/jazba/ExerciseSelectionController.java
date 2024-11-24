package jazba;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExerciseSelectionController {

    @FXML
    private VBox exerciseBox;

    @FXML
    private Button backButton;

    private ListView<Exercise> mainListView;

    public void initialize() {
        backButton.setOnAction(event -> closeWindow());
        populateExercises();
    }

    public void setMainListView(ListView<Exercise> exerciseList) {
        this.mainListView = exerciseList;
    }

    private void populateExercises() {
        // Define some exercises with names, target muscles, and descriptions
        String[][] exercises = {
            {"Bench Press", "Chest, Triceps", "A powerful upper body push exercise."},
            {"Squats", "Quads, Hamstrings, Glutes", "A lower body compound movement."},
            {"Deadlift", "Back, Hamstrings, Glutes", "A full-body strength movement."},
            {"Pull-Ups", "Back, Biceps", "A bodyweight exercise targeting the upper back."},
            {"Bicep Curls", "Biceps", "An isolation exercise for the arms."}
        };

        for (String[] exercise : exercises) {
            VBox exerciseCard = new VBox(10);
            exerciseCard.setStyle("-fx-background-color: #333; -fx-padding: 10px; -fx-background-radius: 10px;");

            // Name label
            Label nameLabel = new Label(exercise[0]);
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

            // Target muscles label
            Label musclesLabel = new Label("Target Muscles: " + exercise[1]);
            musclesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: lightgray;");

            // Description label
            Label descriptionLabel = new Label(exercise[2]);
            descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

            // Select button
            Button selectButton = new Button("Select");
            selectButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
            selectButton.setOnAction(event -> addExerciseToMainList(exercise[0], exercise[1], exercise[2]));

            exerciseCard.getChildren().addAll(nameLabel, musclesLabel, descriptionLabel, selectButton);
            exerciseBox.getChildren().add(exerciseCard);
        }
    }

    private void addExerciseToMainList(String name, String muscles, String description) {
        if (mainListView != null) {
            Exercise exercise = new Exercise(name, muscles, description);
            mainListView.getItems().add(exercise);
        }
        closeWindow();
    }
    

    private void closeWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
package jazba;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ExerciseSelectionController {

    @FXML
    private VBox exerciseBox;

    @FXML
    private Button backButton;

    private CreateWorkoutController createWorkoutController;  // Reference to CreateWorkoutController

    // Setter for CreateWorkoutController
    public void setCreateWorkoutController(CreateWorkoutController controller) {
        this.createWorkoutController = controller;
    }

    // This method will be called when a user selects an exercise
    private void addExerciseToMainList(String exerciseName, String description, String targetMuscles) {
        // Pass the exercise data to CreateWorkoutController
        if (createWorkoutController != null) {
            createWorkoutController.addExerciseToList(exerciseName, description, targetMuscles);
        }
    }

    public void initialize() {
        // Populate exercises and set button actions
        populateExercises();
    }

    private void populateExercises() {
        String[][] exercises = {
            {"Bench Press", "A compound exercise targeting the chest, shoulders, and triceps.", "Chest, Shoulders, Triceps"},
            {"Squats", "A full-body exercise focusing on the legs and core.", "Legs, Core"},
            {"Deadlift", "A compound lift engaging the back, legs, and core.", "Back, Legs, Core"},
            {"Pull-Ups", "A bodyweight exercise for back and biceps.", "Back, Biceps"},
            {"Bicep Curls", "An isolation exercise to build the biceps.", "Biceps"}
        };

        for (String[] exercise : exercises) {
            VBox exerciseRow = new VBox(10); // Card for each exercise
            exerciseRow.setStyle("-fx-background-color: #333; -fx-border-radius: 10; -fx-padding: 15; -fx-spacing: 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5);");

            // Exercise title
            Button titleButton = new Button(exercise[0]);
            titleButton.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
            titleButton.setOnAction(event -> addExerciseToMainList(exercise[0], exercise[1], exercise[2]));

            // Exercise description
            Label descriptionLabel = new Label(exercise[1]);
            descriptionLabel.setStyle("-fx-text-fill: #bbb; -fx-font-size: 14px;");

            // Target muscles label
            Label targetMusclesLabel = new Label("Target Muscles: " + exercise[2]);
            targetMusclesLabel.setStyle("-fx-text-fill: #bbb; -fx-font-size: 14px;");

            exerciseRow.getChildren().addAll(titleButton, descriptionLabel, targetMusclesLabel);
            exerciseBox.getChildren().add(exerciseRow);
        }
    }
}

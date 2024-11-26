package jazba;

import java.util.List;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        loadExercises();
    }

    public void setMainListView(ListView<Exercise> exerciseList) {
        this.mainListView = exerciseList;
    }

    private void loadExercises() {
        // Use a background thread to fetch exercises to prevent UI freeze
        Task<List<Exercise>> fetchTask = new Task<>() {
            @Override
            protected List<Exercise> call() {
                return createWorkoutPresetDAO.fetchExercisesFromDatabase();
            }
        };

        fetchTask.setOnSucceeded(event -> populateExercises(fetchTask.getValue()));
        fetchTask.setOnFailed(event -> {
            // Log or show error if fetching fails
            System.err.println("Failed to fetch exercises: " + fetchTask.getException());
        });

        new Thread(fetchTask).start();
    }

    private void populateExercises(List<Exercise> exercises) {
        if (exercises == null || exercises.isEmpty()) {
            Label noExercisesLabel = new Label("No exercises available.");
            noExercisesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: lightgray;");
            exerciseBox.getChildren().add(noExercisesLabel);
            return;
        }

        for (Exercise exercise : exercises) {
            VBox exerciseCard = new VBox(10);
            exerciseCard.setStyle("-fx-background-color: #333; -fx-padding: 10px; -fx-background-radius: 10px;");

            // Name label
            Label nameLabel = new Label(exercise.getName());
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

            // Muscle groups label
            Label musclesLabel = new Label("Target Muscles: " + exercise.getMuscleGroups());
            musclesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: lightgray;");

            // Description label
            Label descriptionLabel = new Label(exercise.getDescription());
            descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

            // Select button
            Button selectButton = new Button("Select");
            selectButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
            selectButton.setOnAction(event -> addExerciseToMainList(exercise));

            exerciseCard.getChildren().addAll(nameLabel, musclesLabel, descriptionLabel, selectButton);
            exerciseBox.getChildren().add(exerciseCard);
        }
    }

    private void addExerciseToMainList(Exercise exercise) {
        if (mainListView != null) {
            if (!mainListView.getItems().contains(exercise)) {
                mainListView.getItems().add(exercise);
            } else {
                System.out.println("Exercise already added: " + exercise.getName());
            }
        }
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }


}

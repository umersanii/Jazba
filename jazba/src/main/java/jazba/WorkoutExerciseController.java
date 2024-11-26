package jazba;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class WorkoutExerciseController {

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
        // Fetch exercises from the database
        List<Exercise> exercises = createWorkoutPresetDAO.fetchExercisesFromDatabase();

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
            mainListView.getItems().add(exercise);
        }
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}

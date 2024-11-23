import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;

public class CreateWorkoutController {

    @FXML
    private ListView<String> exerciseListView;

    private ObservableList<String> selectedExercises = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Example data for shoulder exercises
        ObservableList<String> shoulderExercises = FXCollections.observableArrayList(
                "Overhead Press", "Lateral Raises", "Front Raises"
        );

        // Set custom cell factory for exercises
        exerciseListView.setItems(shoulderExercises);
        exerciseListView.setCellFactory(param -> new ExerciseListCell());
    }

    @FXML
    private void handleCreateWorkout() {
        // Open dialog to ask for workout name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Workout");
        dialog.setHeaderText("Name Your Workout");
        dialog.setContentText("Workout Name:");

        dialog.showAndWait().ifPresent(workoutName -> {
            System.out.println("Workout created: " + workoutName);
            System.out.println("Exercises: " + selectedExercises);
        });
    }
}

package jazba;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;

public class CreateWorkoutController {

    @FXML
    private Button addExerciseButton;

    @FXML
    private ListView<Exercise> exerciseList;

    @FXML
    private TextField workoutNameField; // Field for workout preset name

    private ExerciseDAO exerciseDAO = new ExerciseDAO();  // Instantiate ExerciseDAO

    @FXML
    public void onAddExerciseClicked(ActionEvent event) {  // Changed to ActionEvent
        openExerciseSelectionPage();
    }

    @FXML
    private Button saveWorkoutPresetButton;

    @FXML
    public void onSaveWorkoutPresetClicked(ActionEvent event) {  // Changed to ActionEvent
        saveWorkoutPreset();
    }

    @FXML
    public void initialize() {
        // Ensure the ListView uses the custom cell factory
        exerciseList.setCellFactory(listView -> new ExerciseCardCell());

        // Action for the "Add Exercise" button
        addExerciseButton.setOnAction(event -> openExerciseSelectionPage());
    }

    private void saveWorkoutPreset() {
        String workoutName = workoutNameField.getText();  // Get the name of the workout preset

        if (workoutName.isEmpty()) {
            showError("Error", "Please enter a name for the workout preset.");
            return;  // Return early to prevent saving
        }

        // Check if the exercise list is empty
        if (exerciseList.getItems().isEmpty()) {
            showError("Error", "No exercises in the workout preset. Please add exercises before saving.");
            return;  // Return early to prevent saving
        }

        // Assuming you have a method to get the logged-in memberID
         // Get logged-in user's memberID

        // Iterate through the exercises and check if all input fields are filled
        for (Exercise exercise : exerciseList.getItems()) {
            int sets = exercise.getSets();
            int reps = exercise.getReps();
            double weight = exercise.getWeight();

            // Check if any of the fields are invalid (e.g., zero or negative values)
            if (sets == 0 || reps == 0 || weight == 0) {
                showError("Error", "Please fill in all fields for each exercise before saving.");
                return;  // Return early to prevent saving
            }

            String exerciseName = exercise.getName();
            String targetMuscles = exercise.getTargetMuscles();
            String description = exercise.getDescription();

            // Call ExerciseDAO to save this data into the workout_preset table
            boolean success = exerciseDAO.saveWorkoutPreset(workoutName, exerciseName, targetMuscles, description, sets, reps, weight);

            if (success) {
                showInfo("Success", "Workout preset saved successfully!");
            } else {
                showError("Error", "Failed to save the workout preset.");
            }
        }
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openExerciseSelectionPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExerciseSelection.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Select Exercise");
            stage.setScene(new Scene(root));
            stage.show();

            // Pass the current exercise list view to the ExerciseSelectionController
            ExerciseSelectionController controller = loader.getController();
            controller.setMainListView(exerciseList);

        } catch (IOException e) {
            showError("Error", "Unable to load the exercise selection page.");
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Mock method to get the logged-in user ID, replace with your actual method

    // Custom cell factory for the exercise list
    private static class ExerciseCardCell extends ListCell<Exercise> {
        private final HBox hbox = new HBox();
        private final ImageView imageView = new ImageView();
        private final VBox vbox = new VBox();
        private final Text exerciseName = new Text();
        private final Text targetMuscles = new Text();
        private final Text description = new Text();
        private final TextField setsField = new TextField();
        private final TextField repsField = new TextField();
        private final TextField weightField = new TextField();
        private final Button removeButton = new Button("Remove");

        public ExerciseCardCell() {
            super();

            // Configure HBox layout
            hbox.setSpacing(10);

            // Configure ImageView
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);

            // Configure TextFields
            setsField.setPromptText("Sets");
            repsField.setPromptText("Reps");
            weightField.setPromptText("Weight");

            // Configure VBox
            vbox.getChildren().addAll(exerciseName, targetMuscles, description, setsField, repsField, weightField);

            // Add components to HBox
            hbox.getChildren().addAll(imageView, vbox, removeButton);

            // Remove button action
            removeButton.setOnAction(event -> {
                if (getListView() != null) {
                    getListView().getItems().remove(getItem());
                }
            });
        }

        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setText(null);
                setGraphic(null);
            } else {
                exerciseName.setText(exercise.getName());
                targetMuscles.setText("Target Muscles: " + exercise.getTargetMuscles());
                description.setText(exercise.getDescription());

                // Set a placeholder image
                Image placeholderImage = new Image("file:/Y:/Season%205/SDA/Jazba/jazba/target/classes/jazba/dumbbell.png");
                imageView.setImage(placeholderImage);

                // Add change listeners to TextFields to update the Exercise object
                setsField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        exercise.setSets(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {
                        exercise.setSets(0);  // Default to 0 if input is invalid
                    }
                });

                repsField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        exercise.setReps(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {
                        exercise.setReps(0);  // Default to 0 if input is invalid
                    }
                });

                weightField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        exercise.setWeight(Double.parseDouble(newValue));
                    } catch (NumberFormatException e) {
                        exercise.setWeight(0);  // Default to 0 if input is invalid
                    }
                });

                setGraphic(hbox);
            }
        }
    }
}

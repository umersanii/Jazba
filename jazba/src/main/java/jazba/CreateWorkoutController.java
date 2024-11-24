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

import java.io.IOException;

public class CreateWorkoutController {

    @FXML
    private Button addExerciseButton;

    @FXML
    private ListView<Exercise> exerciseList; // Use a custom Exercise class for list items

    @FXML
    public void onAddExerciseClicked(MouseEvent event) {
        openExerciseSelectionPage();
    }

    @FXML
    public void initialize() {
        // Ensure the ListView uses the custom cell factory
        exerciseList.setCellFactory(listView -> new ExerciseCardCell());

        // Add a sample exercise to demonstrate the card format
        exerciseList.getItems().add(new Exercise("Sample Exercise", "Sample Muscles", "Sample Description"));

        System.out.println(getClass().getResource("dumbbell.png"));

        // Action for the "Add Exercise" button
        addExerciseButton.setOnAction(event -> openExerciseSelectionPage());
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

                setGraphic(hbox);
            }
        }
    }
}

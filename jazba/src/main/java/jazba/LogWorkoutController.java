package jazba;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LogWorkoutController {

    @FXML
    private TextField workoutNameField;

    @FXML
    private TextField exerciseNameField;

    @FXML
    private TextField setsField;

    @FXML
    private TextField repsField;

    @FXML
    private TextField weightField;

    @FXML
    private Button addExerciseButton;

    @FXML
    private Button saveWorkoutPresetButton;

    @FXML
    private Button SystemGenerateWorkouts;

    @FXML
    private Button UserGeneratedWorkouts;

    @FXML
    private VBox workoutList;

    private WorkoutPreset currentPreset;

    @FXML
    public void initialize() {
        currentPreset = new WorkoutPreset("New Workout");

        // Hook up button actions
        SystemGenerateWorkouts.setOnAction(e -> onSystemGenerateWorkoutsClicked());
       // UserGeneratedWorkouts.setOnAction(e -> onUserGeneratedWorkoutsClicked());
        addExerciseButton.setOnAction(e -> onAddExerciseClicked());
        saveWorkoutPresetButton.setOnAction(e -> onSaveWorkoutPresetClicked());
    }

    // Method to create a workout node from a preset
    private VBox createWorkoutNode(WorkoutPreset preset) {
        VBox presetBox = new VBox();
        presetBox.setStyle("-fx-padding: 15; -fx-spacing: 10; -fx-background-color: #2A2A2A; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #000000, 10, 0.3, 0, 3);");

        Text presetTitle = new Text(preset.getName());
        presetTitle.setStyle("-fx-font-size: 18px; -fx-fill: #FFFFFF; -fx-font-weight: bold;");
        presetBox.getChildren().add(presetTitle);

        VBox exerciseContainer = new VBox();
        exerciseContainer.setStyle("-fx-spacing: 10; -fx-padding-top: 10;");

        for (Exercise exercise1 : preset.getExercises()) {
            exerciseContainer.getChildren().add(createExerciseNode(exercise1));
        }

        presetBox.getChildren().add(exerciseContainer);
        return presetBox;
    }

    private HBox createExerciseNode(Exercise exercise) {
        HBox exerciseBox = new HBox();
        exerciseBox.setStyle("-fx-padding: 10; -fx-spacing: 10; -fx-background-color: #3A3A3A; -fx-background-radius: 5; -fx-alignment: center;");
    
        int defaultSets = exercise.getSets() > 0 ? exercise.getSets() : 3;
        int defaultReps = exercise.getReps() > 0 ? exercise.getReps() : 10;
        double defaultWeight = exercise.getWeight() > 0 ? exercise.getWeight() : 0.0;

        Text exerciseTitle = new Text(exercise.getName());
        System.out.println(exercise.getName());

        exerciseTitle.setStyle("-fx-font-size: 20px; -fx-fill: #FFFFFF; -fx-font-weight: bold;");

        VBox titleBox = new VBox();
        titleBox.setStyle("-fx-padding: 5; -fx-spacing: 5;");
        titleBox.getChildren().add(exerciseTitle);

        HBox inputBox = new HBox();
        inputBox.setStyle("-fx-spacing: 10; -fx-padding-top: 10;");
        
        TextField setsField = new TextField(String.valueOf(defaultSets));
        setsField.setPrefWidth(50);
        setsField.setStyle("-fx-background-radius: 5; -fx-border-color: #5A5A5A; -fx-border-radius: 5; -fx-text-fill: #000000; -fx-prompt-text-fill: #B0B0B0; -fx-background-color: #ffffff;");
    
        TextField repsField = new TextField(String.valueOf(defaultReps));
        repsField.setPrefWidth(50);
        repsField.setStyle("-fx-background-radius: 5; -fx-border-color: #5A5A5A; -fx-border-radius: 5; -fx-text-fill: #000000; -fx-prompt-text-fill: #B0B0B0; -fx-background-color: #ffffff;");
    
        TextField weightField = new TextField(String.valueOf(defaultWeight));
        weightField.setPrefWidth(60);
        weightField.setStyle("-fx-background-radius: 5; -fx-border-color: #5A5A5A; -fx-border-radius: 5; -fx-text-fill: #000000; -fx-prompt-text-fill: #B0B0B0; -fx-background-color: #ffffff;");
    
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: #FFFFFF; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            try {
                exercise.setSets(Integer.parseInt(setsField.getText().trim()));
                exercise.setReps(Integer.parseInt(repsField.getText().trim()));
                exercise.setWeight(Double.parseDouble(weightField.getText().trim()));
            } catch (NumberFormatException ex) {
                showError("Error", "Please enter valid numbers for sets, reps, and weight.");
            }
        });

        inputBox.getChildren().addAll(
            new Text("Sets: "), setsField,
            new Text("Reps: "), repsField,
            new Text("Weight (kg): "), weightField,
            saveButton
        );

        VBox exerciseContainer = new VBox();
        exerciseContainer.setStyle("-fx-spacing: 10; -fx-padding-top: 10;");
        exerciseContainer.getChildren().addAll(titleBox, inputBox);

        exerciseBox.getChildren().add(exerciseContainer);
        return exerciseBox;
    }

@FXML
private void onSystemGenerateWorkoutsClicked() {
    workoutList.getChildren().clear();
    
    try {
        LogWorkoutDAO dao = new LogWorkoutDAO();
        int memberID = -1; // Example system member ID for system-generated presets
        List<WorkoutPreset> systemWorkouts = dao.getWorkoutPresetsByMemberID(memberID);

        for (WorkoutPreset systemPreset : systemWorkouts) {
            // Fetch exercises for the specific workoutPresetID

            // Add the preset and its exercises to the workout list
            workoutList.getChildren().add(createWorkoutNode(systemPreset));
        }
    } catch (SQLException e) {
        showError("Database Error", "Failed to load system-generated workouts.");
    }
}


// @FXML
// private void onUserGeneratedWorkoutsClicked() {
//     workoutList.getChildren().clear();
    
//     try {
//         int memberID = UserSession.getLoggedInUserID(); // Logged-in user's member ID
//         LogWorkoutDAO dao = new LogWorkoutDAO();
//         List<WorkoutPreset> userWorkouts = dao.getWorkoutPresetsByMemberID(memberID);

//         for (WorkoutPreset userPreset : userWorkouts) {
//             // Fetch exercises for the specific workoutPresetID
//             List<Exercise> exercises = new ArrayList<Exercise>();
//             Exercise E = new Exercise("1", null, null, null, memberID, null, memberID, memberID, memberID);
//             exercises.add(E);
//             // Add the preset and its exercises to the workout list
//             workoutList.getChildren().add(createWorkoutNode(userWorkouts, exercises));
//         }
//     } catch (SQLException e) {
//         showError("Database Error", "Failed to load user-generated workouts.");
//     }
// }


private List<Exercise> getExercisesByWorkoutPresetID(int memberID) {
    try {
        LogWorkoutDAO dao = new LogWorkoutDAO();
        return dao.getExercisesByWorkoutPresetID(memberID);
    } catch (SQLException e) {
        showError("Database Error", "Failed to load user-generated workouts.");
        return List.of();
    }
}

    private List<WorkoutPreset> getWorkoutPresetsByMemberID(int memberID) {
        try {
            LogWorkoutDAO dao = new LogWorkoutDAO();
            return dao.getWorkoutPresetsByMemberID(memberID);
        } catch (SQLException e) {
            showError("Database Error", "Failed to load user-generated workouts.");
            return List.of();
        }
    }

    @FXML
    private void onAddExerciseClicked() {
        String name = exerciseNameField.getText().trim();
        int sets;
        int reps;
        double weight;

        try {
            sets = Integer.parseInt(setsField.getText().trim());
            reps = Integer.parseInt(repsField.getText().trim());
            weight = Double.parseDouble(weightField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Error", "Please enter valid numbers for sets, reps, and weight.");
            return;
        }

        if (name.isEmpty()) {
            showError("Error", "Exercise name cannot be empty.");
            return;
        }

        Exercise newExercise = new Exercise(name, name, name, name, sets, name, reps, reps, weight);
        currentPreset.addExercise(newExercise);

        workoutList.getChildren().clear();
      //  workoutList.getChildren().add(createWorkoutNode(currentPreset));

        exerciseNameField.clear();
        setsField.setText("3");
        repsField.setText("10");
        weightField.setText("0.0");
    }

    @FXML
    private void onSaveWorkoutPresetClicked() {
        showInfo("Save", "Workout presets saved successfully.");
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

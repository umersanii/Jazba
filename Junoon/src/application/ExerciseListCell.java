import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

public class ExerciseListCell extends ListCell<String> {
    @Override
    protected void updateItem(String exercise, boolean empty) {
        super.updateItem(exercise, empty);

        if (empty || exercise == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Exercise name
            Text exerciseName = new Text(exercise);

            // "+" button
            Button addButton = new Button("+");
            addButton.setOnAction(event -> {
                // Open dialog for sets/reps/rest
                System.out.println("Add exercise: " + exercise);
            });

            // Layout
            HBox hbox = new HBox(10, exerciseName, addButton);
            setGraphic(hbox);
        }
    }
}

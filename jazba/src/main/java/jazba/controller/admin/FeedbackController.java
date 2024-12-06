package jazba.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class FeedbackController {

    @FXML
    private ListView<String> feedbackListView;

    @FXML
    private TextArea feedbackDetailsArea;

    @FXML
    private ChoiceBox<Integer> ratingChoiceBox;

    private final ObservableList<String> feedbacks = FXCollections.observableArrayList();

    // Initialize the controller
    @FXML
    public void initialize() {
        // Initialize rating filter options
        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingChoiceBox.setValue(1); // Default to rating 1
        fetchFeedback(null); // Fetch all feedback initially
    }

    // Apply filter based on selected rating
    @FXML
    private void applyFilter() {
        Integer selectedRating = ratingChoiceBox.getValue();
        if (selectedRating != null) {
            fetchFeedback(selectedRating);
        }
    }

    // Fetch feedback from the database
    private void fetchFeedback(Integer rating) {
        feedbacks.clear();
        feedbackDetailsArea.clear(); // Clear feedback details area
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JazbaDB", "root", "2cool4skool")) {
            String query = "SELECT id, userID, feedbackMessage, rating, timestamp FROM Feedback";
            if (rating != null) {
                query += " WHERE rating = ?";
            }
            PreparedStatement stmt = conn.prepareStatement(query);

            if (rating != null) {
                stmt.setInt(1, rating);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("feedbackMessage");
                feedbacks.add("Feedback ID: " + id + " - Rating: " + rs.getInt("rating"));
            }

            feedbackListView.setItems(feedbacks);

        } catch (SQLException e) {
            showError("Error fetching feedback: " + e.getMessage());
        }
    }

    // Show feedback details when a feedback item is selected
    @FXML
    private void showFeedbackDetails(MouseEvent event) {
        String selectedFeedback = feedbackListView.getSelectionModel().getSelectedItem();
        if (selectedFeedback != null) {
            int id = Integer.parseInt(selectedFeedback.split(": ")[1].split(" - ")[0]);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JazbaDB", "root", "2cool4skool")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Feedback WHERE id = ?");
                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String feedbackMessage = rs.getString("feedbackMessage");
                    int rating = rs.getInt("rating");
                    Timestamp timestamp = rs.getTimestamp("timestamp");

                    String details = "Feedback ID: " + id + "\n" +
                                     "Message: " + feedbackMessage + "\n" +
                                     "Rating: " + rating + "\n" +
                                     "Timestamp: " + timestamp.toString();

                    feedbackDetailsArea.setText(details);
                }

            } catch (SQLException e) {
                showError("Error fetching feedback details: " + e.getMessage());
            }
        }
    }

    // Show an error message in an alert box
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear feedback details and reset the filter
    @FXML
    private void clearFeedback() {
        feedbackListView.getSelectionModel().clearSelection();
        feedbackDetailsArea.clear();
        ratingChoiceBox.setValue(1); // Reset to default rating
        fetchFeedback(null); // Fetch all feedback
    }

    // Close the feedback view
    @FXML
    private void closeFeedback() {
        // Close the window (this could be customized based on the app's behavior)
        feedbackListView.getScene().getWindow().hide();
    }
}

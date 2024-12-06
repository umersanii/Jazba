package jazba.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class SystemLogsController {

    @FXML
    private ListView<String> logsListView;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextArea logDetailsArea;

    private final ObservableList<String> logs = FXCollections.observableArrayList();

    // Initialize the controller
    @FXML
    public void initialize() {
        // Populate filter options
        filterComboBox.getItems().addAll("All", "Error", "Activity", "Notification");
        filterComboBox.setValue("All"); // Default to "All"
        fetchLogs("All"); // Fetch all logs initially
    }

    // Fetch logs based on the selected filter
    @FXML
    private void applyFilter() {
        String selectedFilter = filterComboBox.getValue();
        if (selectedFilter != null) {
            fetchLogs(selectedFilter);
        }
    }

    // Fetch logs from the database
    private void fetchLogs(String filter) {
        logs.clear();
        logDetailsArea.clear(); // Clear log details area
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JazbaDB", "root", "2cool4skool")) {
            String query = "SELECT id, logType, message, timestamp FROM SystemLogs";
            if (!"All".equals(filter)) {
                query += " WHERE logType = ?";
            }
            PreparedStatement stmt = conn.prepareStatement(query);

            if (!"All".equals(filter)) {
                stmt.setString(1, filter);
            }

            ResultSet rs = stmt.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("logType");
                String message = rs.getString("message");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                // Modify how the logs are added to the list, including the message
                logs.add(String.format("[%s] %s - ID: %d - %s", type, timestamp.toLocalDateTime().format(formatter), id, message));
            }

            logsListView.setItems(logs);

        } catch (SQLException e) {
            showError("Error fetching logs: " + e.getMessage());
        }
    }

    // Clear all logs or logs of a specific type
    @FXML
    private void clearLogs() {
        String selectedFilter = filterComboBox.getValue();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JazbaDB", "root", "2cool4skool")) {
            String query = "DELETE FROM SystemLogs";
            if (!"All".equals(selectedFilter)) {
                query += " WHERE logType = ?";
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            if (!"All".equals(selectedFilter)) {
                stmt.setString(1, selectedFilter);
            }

            int rowsAffected = stmt.executeUpdate();
            logs.clear(); // Clear the displayed logs
            logDetailsArea.clear(); // Clear log details area

            showInfo(rowsAffected + " log(s) cleared.");
            fetchLogs("All"); // Refresh the list

        } catch (SQLException e) {
            showError("Error clearing logs: " + e.getMessage());
        }
    }

    // Show log details in the text area when a log is selected
    @FXML
    private void showLogDetails() {
        String selectedLog = logsListView.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            int id = Integer.parseInt(selectedLog.split("ID: ")[1].split(" - ")[0]); // Extract ID from the selected log
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JazbaDB", "root", "2cool4skool")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SystemLogs WHERE id = ?");
                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    logDetailsArea.setText("Log ID: " + rs.getInt("id") +
                            "\nType: " + rs.getString("logType") +
                            "\nMessage: " + rs.getString("message") +
                            "\nTimestamp: " + rs.getTimestamp("timestamp"));
                }

            } catch (SQLException e) {
                showError("Error fetching log details: " + e.getMessage());
            }
        }
    }

    // Utility method for showing errors
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method for showing information
    private void showInfo(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

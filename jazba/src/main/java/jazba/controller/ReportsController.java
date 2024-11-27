package jazba.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportsController {

    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private TextArea reportOutputArea;

    // Initialize ComboBox with report options
    public void initialize() {
        reportTypeComboBox.getItems().addAll(
                "Membership Overview",
                "Revenue Reports",
                "Workout Reports",
                "Member Achievements"
        );
    }

    // Generate report based on selected type
    @FXML
    private void generateReport() {
        String selectedReport = reportTypeComboBox.getValue();

        if (selectedReport == null) {
            reportOutputArea.setText("Please select a report type.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JazbaDB", "root", "Tabodi123*")) {
            System.out.println("Database connected successfully.");

            String reportQuery = getQueryForReport(selectedReport);
            if (reportQuery == null) {
                reportOutputArea.setText("Invalid report type selected.");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(reportQuery);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) { // Check if the result set is empty
                reportOutputArea.setText("No data found for the selected report.");
                return;
            }

            // Construct report content
            StringBuilder reportContent = new StringBuilder();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    reportContent.append(rs.getMetaData().getColumnLabel(i))
                            .append(": ")
                            .append(rs.getString(i))
                            .append(" | ");
                }
                reportContent.append("\n");
            }

            reportOutputArea.setText(reportContent.toString());

            // Save the generated report to the database
            saveReportToDatabase(selectedReport, reportContent.toString(), conn);

        } catch (Exception e) {
            reportOutputArea.setText("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Map report type to SQL queries
    private String getQueryForReport(String reportType) {
        switch (reportType) {
            case "Membership Overview":
                return "SELECT username, email, membership_status, registration_date FROM Member";
            case "Revenue Reports":
                return "SELECT SUM(amount) AS TotalRevenue FROM Payment";
            case "Workout Reports":
                return "SELECT m.username, w.date, w.time, w.preset, w.notes, s.totalSets, s.totalReps, s.totalWeight "
                        + "FROM Member m "
                        + "JOIN Workout w ON m.id = w.memberID "
                        + "JOIN Stats s ON w.id = s.workoutID";
            case "Member Achievements":
                return "SELECT m.username, a.title, a.dateUnlocked, a.badge "
                        + "FROM Member m "
                        + "JOIN Achievement a ON m.id = a.memberID";
            default:
                return null;
        }
    }

  // Save report into the database, ensuring uniqueness
private void saveReportToDatabase(String reportType, String content, Connection conn) throws Exception {
    // Check if the same report content already exists
    String checkQuery = "SELECT COUNT(*) FROM Reports WHERE reportType = ? AND reportContent = ?";
    PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
    checkStmt.setString(1, reportType);
    checkStmt.setString(2, content);
    ResultSet rs = checkStmt.executeQuery();
    rs.next();
    int count = rs.getInt(1);

    if (count > 0) {
        System.out.println("Duplicate report detected. Report not saved.");
        return; // Exit the method without saving
    }

    // Insert the new report into the database
    String insertQuery = "INSERT INTO Reports (reportType, reportContent, generatedBy) VALUES (?, ?, ?)";
    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
    insertStmt.setString(1, reportType);
    insertStmt.setString(2, content);
    insertStmt.setInt(3, 1); // Assuming admin ID = 1 for now
    insertStmt.executeUpdate();
    System.out.println("Report saved to database.");
}


    // Back button placeholder
    @FXML
    private void goBack() {
        System.out.println("Back button clicked.");
    }
}

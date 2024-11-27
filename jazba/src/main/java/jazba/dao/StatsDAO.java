package jazba.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.util.*;


public class StatsDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2cool4skool";

    // SQL query to fetch workout stats for exercises, filtered by memberID
    private static final String QUERY = "SELECT exerciseName, SUM(reps) AS total_reps, SUM(sets) AS total_sets, SUM(weight) AS total_weight " +
                                        "FROM stats WHERE memberID = ? AND Date >= CURDATE() - INTERVAL (?) DAY " +
                                        "GROUP BY exerciseName ORDER BY total_weight DESC";  // Fetch all exercises

    // SQL query to fetch average volume over the last 30 days
    private static final String LINE_CHART_QUERY = 
    "SELECT DATE(Date) AS workout_date, SUM(reps * weight * sets) AS total_volume " +
    "FROM stats WHERE memberID = ? AND Date >= CURDATE() - INTERVAL (?) DAY " +
    "GROUP BY workout_date ORDER BY workout_date ASC";

    // SQL query to fetch the number of days lifted
    private static final String DAYS_LIFTED_QUERY = 
    "SELECT COUNT(DISTINCT DATE(Date)) AS days_lifted " +
    "FROM stats WHERE memberID = ? AND Date >= CURDATE() - INTERVAL (?) DAY";

    // Method to fetch PieChart data for a specific user (top 3 favorite exercises)
    public ObservableList<PieChart.Data> getPieChartData(int memberId, int days) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        Map<String, Integer> exerciseScores = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(QUERY)) {
            
            stmt.setInt(1, memberId);
            stmt.setInt(2, days);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String exerciseName = rs.getString("exerciseName");
                    int totalReps = rs.getInt("total_reps");
                    int totalSets = rs.getInt("total_sets");
                    int totalWeight = rs.getInt("total_weight");

                    // Calculate score using the formula: (a * totalReps) + (b * totalSets) + (c * totalWeight)
                    // For simplicity, assume equal weights for each factor, adjust 'a', 'b', 'c' as necessary
                    int score = (1 * totalReps) + (1 * totalSets) + (1 * totalWeight);

                    exerciseScores.put(exerciseName, score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Sort exercises by their scores in descending order and take the top 3
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(exerciseScores.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Add top 3 exercises to pieData
        for (int i = 0; i < Math.min(3, sortedEntries.size()); i++) {
            Map.Entry<String, Integer> entry = sortedEntries.get(i);
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        return pieData;
    }

    // Method to fetch BarChart data for a specific user
    public XYChart.Series<String, Number> getBarChartData(int memberId, int days) {
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Top Exercises Over " + days + " Days");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(QUERY)) {
            
            stmt.setInt(1, memberId);
            stmt.setInt(2, days);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String exerciseName = rs.getString("exerciseName");
                    int totalWeight = rs.getInt("total_weight");

                    barSeries.getData().add(new XYChart.Data<>(exerciseName, totalWeight));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barSeries;
    }

    // Method to fetch LineChart data for a specific user (average volume over the past 30 days)
    public XYChart.Series<String, Number> getLineChartData(int memberId, int days) {
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Average Volume Over " + days + " Days");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(LINE_CHART_QUERY)) {
            
            stmt.setInt(1, memberId);
            stmt.setInt(2, days);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String date = rs.getString("workout_date");
                    int totalVolume = rs.getInt("total_volume");

                    lineSeries.getData().add(new XYChart.Data<>(date, totalVolume));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineSeries;
    }

    // Method to get the number of days lifted in the last 30 days
    public int getDaysLifted(int memberId, int days) {
        int daysLifted = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(DAYS_LIFTED_QUERY)) {
            
            stmt.setInt(1, memberId);
            stmt.setInt(2, days);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    daysLifted = rs.getInt("days_lifted");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daysLifted;
    }

    // Method to fetch reps, sets, weight, and days logged in for a specific user
public Map<String, String> getUserStats(int memberId) {
    Map<String, String> stats = new HashMap<>();

    String query = "SELECT SUM(reps) AS total_reps, SUM(sets) AS total_sets, SUM(weight) AS total_weight, " +
                   "COUNT(DISTINCT DATE(Date)) AS days_logged_in " +
                   "FROM stats WHERE memberID = ?";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
         PreparedStatement stmt = connection.prepareStatement(query)) {
        
        stmt.setInt(1, memberId);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                stats.put("total_reps", String.valueOf(rs.getInt("total_reps")));
                stats.put("total_sets", String.valueOf(rs.getInt("total_sets")));
                stats.put("total_weight", String.valueOf(rs.getInt("total_weight")));
                stats.put("days_logged_in", String.valueOf(rs.getInt("days_logged_in")));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return stats;
}

}


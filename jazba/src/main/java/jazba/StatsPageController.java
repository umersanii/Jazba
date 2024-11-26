package jazba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class StatsPageController {

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private ComboBox<String> lineChartDropdown;

    @FXML
    private ComboBox<String> barChartDropdown;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private void handleBarChartSelection(ActionEvent event) {
        String selectedOption = barChartDropdown.getValue();
        System.out.println("Selected Bar Chart Option: " + selectedOption);
        // Add logic to update the chart based on the selection
    }

    @FXML
    private void handleLineChartSelection(ActionEvent event) {
        String selectedOption = lineChartDropdown.getValue();
        System.out.println("Selected Line Chart Option: " + selectedOption);
        // Add logic to update the line chart based on the selection
    }

    private XYChart.Series<String, Number> barSeries; // Keep reference to the BarChart series
    private ObservableList<PieChart.Data> pieData; // Keep reference to PieChart data

    @FXML
    private void navigateToStatsPage(ActionEvent event) {
        System.out.println("Navigating to Stats Page");
        // Your navigation logic goes here
    }

    @FXML
    public void addHoverEffect(MouseEvent event) {
        // Example hover effect on the chart
        System.out.println("Hover effect triggered");
    }

    @FXML
    public void showUserMenu(MouseEvent event) {
        System.out.println("User menu clicked!");
        // Logic for displaying the user menu
    }

    @FXML
    public void showNotifications(MouseEvent event) {
        // Your logic for showing notifications
        System.out.println("Notifications clicked!");
    }

    @FXML
    private void initialize() {
        // Initialize both charts with data
        initializeBarChart();
        initializePieChart();

        ObservableList<String> choices = FXCollections.observableArrayList(
            "Choice 1", "Choice 2", "Choice 3"
        );

        // Set the observable list as items for the ComboBox
        lineChartDropdown.setItems(choices);
        
        // Set the observable list as items for the ComboBox
        barChartDropdown.setItems(choices);

    }

    private void initializeBarChart() {
        // Initialize the bar chart series once
        barSeries = new XYChart.Series<>();
        barSeries.setName("Workout Stats");

        // Example workout data
        barSeries.getData().add(new XYChart.Data<>("Push-ups", 120));
        barSeries.getData().add(new XYChart.Data<>("Squats", 90));
        barSeries.getData().add(new XYChart.Data<>("Lunges", 75));
        barSeries.getData().add(new XYChart.Data<>("Deadlifts", 80));

        // Clear existing data and add the series
        stackedBarChart.getData().clear();
        stackedBarChart.getData().add(barSeries);
    }

    private void initializePieChart() {
        // Initialize PieChart data
        pieData = FXCollections.observableArrayList(
            new PieChart.Data("Push-ups", 120),
            new PieChart.Data("Squats", 90),
            new PieChart.Data("Lunges", 75),
            new PieChart.Data("Deadlifts", 80)
        );

        // Set the pie chart data
        pieChart.setData(pieData);

        // Add mouse click event listener to show data
        for (final PieChart.Data data : pieData) {
            data.getNode().setOnMousePressed(e -> {
                System.out.println(data.getName() + ": " + data.getPieValue());
            });
        }
    }

    // Add a method to dynamically populate data from a user's workout stats
    public void populateStats(String exerciseName, int reps, int sets, int weight) {
        // Assuming you have a function to dynamically update the charts
        updateBarChart(exerciseName, reps);
        updatePieChart(exerciseName, reps);
    }

    private void updateBarChart(String exerciseName, int reps) {
        // Add new data to the bar chart
        barSeries.getData().add(new XYChart.Data<>(exerciseName, reps)); // Adding to existing series
    }

    private void updatePieChart(String exerciseName, int reps) {
        // Check if the exercise already exists in the PieChart data
        boolean exists = false;
        for (PieChart.Data data : pieData) {
            if (data.getName().equals(exerciseName)) {
                data.setPieValue(data.getPieValue() + reps); // Update the existing entry
                exists = true;
                break;
            }
        }
        
        // If the exercise doesn't exist, add new data
        if (!exists) {
            pieData.add(new PieChart.Data(exerciseName, reps));
        }
    }
}

package jazba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class StatsPageController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private void navigateToStatsPage(ActionEvent event) {
    
System.out.println('1');

    }

     @FXML
    public void addHoverEffect(MouseEvent event) {
        // Add your hover effect logic here
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
    }

    private void initializeBarChart() {
        // Create a series for bar chart data
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Workout Stats");

        // Add data to the series
        barSeries.getData().add(new XYChart.Data<>("Push-ups", 120));
        barSeries.getData().add(new XYChart.Data<>("Squats", 90));
        barSeries.getData().add(new XYChart.Data<>("Lunges", 75));

        // Add the series to the bar chart
        barChart.getData().clear(); // Clear any previous data
        barChart.getData().add(barSeries);
    }

    private void initializePieChart() {
        // Create an observable list for pie chart data
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Push-ups", 120),
            new PieChart.Data("Squats", 90),
            new PieChart.Data("Lunges", 75)
        );

        // Set the pie chart data
        pieChart.setData(pieData);
    }
}

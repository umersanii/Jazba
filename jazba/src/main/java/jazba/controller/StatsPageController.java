package jazba.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jazba.dao.StatsDAO;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.application.Platform;



import javafx.application.Platform;

public class StatsPageController {

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label repsValue;

    @FXML
    private Label setsValue;

    @FXML
    private Label weightValue;

    @FXML
    private Label DaysloggedInValue;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private ComboBox<String> lineChartDropdown;

    @FXML
    private ComboBox<String> barChartDropdown;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label barChartLabel;

    @FXML
    private Label pieChartLabel;

    @FXML
    private Label lineChartLabel;

    @FXML
    private VBox statsContainer;
    
    @FXML
    private Button shareButton;  // Your share button in FXML

// Inside the controller or in the FXML file:


    private XYChart.Series<String, Number> barSeries;
    private XYChart.Series<String, Number> lineSeries;
    private ObservableList<PieChart.Data> pieData;

    private StatsDAO statsDAO;
    private int loggedInMemberID = 1;

    public StatsPageController() {
        statsDAO = new StatsDAO();
        loggedInMemberID = 1;
    }

    @FXML
    private void handleBarChartSelection(ActionEvent event) {
        String selectedOption = barChartDropdown.getValue();

        updateBarChart(selectedOption);
        updateLineChartLabel(selectedOption);

    }

    @FXML
    private void handleLineChartSelection(ActionEvent event) {
        String selectedOption = lineChartDropdown.getValue();

        updateLineChart(selectedOption);
        updateBarChartLabel(selectedOption);
    }

    @FXML
    private void navigateToStatsPage(ActionEvent event) {
        System.out.println("Navigating to Stats Page");
    }

    @FXML
    public void addHoverEffect(MouseEvent event) {
        System.out.println("Hover effect triggered");
    }

    @FXML
    public void showUserMenu(MouseEvent event) {
        System.out.println("User menu clicked!");
    }

    @FXML
    public void showNotifications(MouseEvent event) {
        System.out.println("Notifications clicked!");
    }

    @FXML
    private void initialize() {
        barSeries = statsDAO.getBarChartData(loggedInMemberID, 7);
        initializeBarChart(barSeries);
        initializePieChart();
        lineSeries = statsDAO.getLineChartData(loggedInMemberID, 7);
        initializeLineChart(lineSeries);

        ObservableList<String> choices = FXCollections.observableArrayList("A Week", "A Month", "A Year");
        lineChartDropdown.setItems(choices);
        barChartDropdown.setItems(choices);
        shareButton.setOnAction(this::handleShareButtonAction);


        // Fetch stats from the database and update the labels
        Map<String, String> userStats = statsDAO.getUserStats(loggedInMemberID);

        // Update the labels with the fetched values
        repsValue.setText(userStats.getOrDefault("total_reps", "0"));
        setsValue.setText(userStats.getOrDefault("total_sets", "0"));
        weightValue.setText(userStats.getOrDefault("total_weight", "0") + " kg");
        DaysloggedInValue.setText(userStats.getOrDefault("days_logged_in", "0") + " days");
 
    }

    private void initializeBarChart(Series<String, Number> barSeries) {

        stackedBarChart.getData().clear();
        stackedBarChart.getData().add(barSeries);

    }

    private void initializePieChart() {
        pieData = statsDAO.getPieChartData(loggedInMemberID, 365);

        pieChart.setData(pieData);

        for (final PieChart.Data data : pieData) {
            data.getNode().setOnMousePressed(e -> {
                System.out.println(data.getName() + ": " + data.getPieValue());
            });
        }

        Platform.runLater(() -> {
            pieChart.layout();  // Forces layout update
        });
    }

    private void initializeLineChart(Series<String, Number> lineSeries) {
        // lineSeries = statsDAO.getLineChartData(loggedInMemberID);

        lineChart.getData().clear();
        lineChart.getData().add(lineSeries);


        Platform.runLater(() -> {
            lineChart.layout();  // Forces layout update
        });
    }


    
    

    private void updateBarChart(String selectedOption) {
        System.out.println("Updating bar chart for " + selectedOption);
        int daysLifted = 0;

        if (selectedOption.equals("A Week")) {
            initializeBarChart(statsDAO.getBarChartData(loggedInMemberID, 7));


        } else if (selectedOption.equals("A Month")) {
            initializeBarChart(statsDAO.getBarChartData(loggedInMemberID, 30));
            



        } else if (selectedOption.equals("A Year")) {
            initializeBarChart(statsDAO.getBarChartData(loggedInMemberID, 365));



        }

        barSeries.getData().clear();
        barSeries.getData().add(new XYChart.Data<>(selectedOption, daysLifted));

        Platform.runLater(() -> {
            stackedBarChart.layout();  // Forces layout update

        });
    }

    private void updateLineChart(String selectedOption) {

        System.out.println(selectedOption.equals("A Week"));
        if (selectedOption.equals("A Week")) {
            initializeLineChart(statsDAO.getLineChartData(loggedInMemberID, 7));

        } else if (selectedOption.equals("A Month")) {
        System.out.println("asdasdasd line chart for " + selectedOption);

            initializeLineChart(statsDAO.getLineChartData(loggedInMemberID, 30));


        } else if (selectedOption.equals("A Year")) {
            initializeLineChart(statsDAO.getLineChartData(loggedInMemberID, 365));


        }
    }

    private void updateBarChartLabel(String selectedOption) {
        if (selectedOption.equals("A Week")) {
            int daysLifted = statsDAO.getDaysLifted(loggedInMemberID, 7);
            barChartLabel.setText("Days Lifted in Last Week: " + daysLifted);

        } else if (selectedOption.equals("A Month")) {
            int daysLifted = statsDAO.getDaysLifted(loggedInMemberID, 30);
            barChartLabel.setText("Days Lifted in Last Month: " + daysLifted);


        } else if (selectedOption.equals("A Year")) {
            int daysLifted = statsDAO.getDaysLifted(loggedInMemberID, 365);
            barChartLabel.setText("Days Lifted in Last Year: " + daysLifted);


        }
        
    }

    private void updatePieChartLabel() {
        int totalExercises = pieData.stream().mapToInt(data -> (int) data.getPieValue()).sum();
        pieChartLabel.setText("Total Exercises: " + totalExercises);
    }

    private void updateLineChartLabel(String selectedOption) {
        int totalVolume = lineSeries.getData().stream().mapToInt(data -> (int) data.getYValue()).sum();
        

        if (selectedOption.equals("A Week")) {
            lineChartLabel.setText("Total Volume in Last 30 Days: " + totalVolume);

        } else if (selectedOption.equals("A Month")) {
            lineChartLabel.setText("Total Volume in Last 30 Days: " + totalVolume);


        } else if (selectedOption.equals("A Year")) {
            lineChartLabel.setText("Total Volume in Last 30 Days: " + totalVolume);


        }
    }


    private void handleShareButtonAction(ActionEvent event) {
        try {
            // Capture the entire scene or just the charts as an image
            WritableImage snapshot = takeSnapshot(statsContainer);  // You can replace 'stackedBarChart' with root node or any chart

            // Open a file chooser to allow the user to select the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                System.out.println("File selected: " + file.getAbsolutePath());
                saveImageToFile(snapshot, file);
            } else {
                System.out.println("No file selected!");
}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private WritableImage takeSnapshot(Node node) {
        // Create a SnapshotParameters object to customize how the image is captured
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.WHITESMOKE);  // Transparent background
        WritableImage snapshot = node.snapshot(params, null);  // Capture the snapshot of the node (e.g., chart)
        System.out.println("Snapshot taken: " + snapshot.getWidth() + "x" + snapshot.getHeight());
        return snapshot;
    }
    private void saveImageToFile(WritableImage image, File file) throws IOException {
        // Convert WritableImage to BufferedImage manually without SwingFXUtils
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
        // Loop through every pixel and get the color data
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, image.getPixelReader().getColor(x, y).hashCode());
            }
        }
    
        // Log the size of the image being saved
        System.out.println("Saving image to file: " + file.getAbsolutePath());
    
        // Save the image as PNG
        boolean isSaved = ImageIO.write(bufferedImage, "PNG", file);
        if (isSaved) {
            System.out.println("Image successfully saved.");
        } else {
            System.out.println("Failed to save image.");
        }
    }
    
}

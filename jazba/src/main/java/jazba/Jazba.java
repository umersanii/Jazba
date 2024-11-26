package jazba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Jazba extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Connect to the database
            Connection conn = DB.connectToDB();
            DB.createDBAndTables(); // Create the database and tables if they don't exist
            if (conn != null) {
                // Proceed with the application if the connection is successful
                Parent root = FXMLLoader.load(getClass().getResource("StatsPage.fxml"));
                SceneManager.setStage(primaryStage); // Set stage for SceneManager
                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Fitness Tracker App");
                primaryStage.show();
            } else {
                System.out.println("Database connection failed. Application will not start.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
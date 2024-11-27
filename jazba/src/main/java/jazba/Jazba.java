package jazba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jazba.models.SceneManager;
import jazba.utils.DB;

import java.sql.Connection;

import javax.print.DocFlavor.URL;

public class Jazba extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Connect to the database
            Connection conn = DB.connectToDB();
            DB.createDBAndTables(); // Create the database and tables if they don't exist
            if (conn != null) {
                // Proceed with the application if the connection is successful
                java.net.URL resource = getClass().getResource("/view/MainScene.fxml");
                if (resource != null) {
                    System.out.println("FXML file found: " + resource.toExternalForm());
                } else {
                    System.out.println("FXML file not found. Check the path!");
                }
                System.out.println("Looking for FXML file at: " + getClass().getResource("MainScene.fxml"));

                Parent root = FXMLLoader.load(getClass().getResource("view/MainScene.fxml"));
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

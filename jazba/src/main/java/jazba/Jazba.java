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

                Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
                SceneManager.setStage(primaryStage); 
                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Jazba");
                primaryStage.setResizable(false); 
                primaryStage.setMaximized(false); 
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

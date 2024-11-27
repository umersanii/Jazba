package jazba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = "Tabodi123*"; // Change to your MySQL password
    private static final String DB_NAME = "JazbaDB";

    public static Connection connectToDB() {
        try {
            // First, connect to MySQL server (without specifying the database)
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
            return null;
        }
    }

    public static void createDBAndTables() {
        Connection conn = connectToDB();
    
        if (conn == null) {
            System.out.println("Could not connect to MySQL server.");
            return;
        }
    
        try (Statement stmt = conn.createStatement()) {
            // Step 1: Create the database if it doesn't exist
            String createDBSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(createDBSQL);
            System.out.println("Database created (or already exists).");
    
            // Step 2: Explicitly select the database
            stmt.executeUpdate("USE " + DB_NAME); // Ensure the database is selected
    
            // Step 3: Create tables one by one
            String[] createTableSQLs = {
                "CREATE TABLE IF NOT EXISTS Admin ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(255), "
                + "email VARCHAR(255)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Member ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(255), "
                + "email VARCHAR(255), "
                + "password VARCHAR(255), "
                + "membership_status VARCHAR(50), "
                + "registration_date DATE) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Profile ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "height INT, "
                + "weight INT, "
                + "age INT, "
                + "fitnessLevel VARCHAR(50), "
                + "memberID INT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Membership ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "startDate DATE, "
                + "endDate DATE, "
                + "paymentStatus VARCHAR(50), "
                + "memberID INT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Achievement ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255), "
                + "description TEXT, "
                + "criteria TEXT, "
                + "dateUnlocked DATE, "
                + "badge VARCHAR(255), "
                + "memberID INT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Notification ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "type VARCHAR(50), "
                + "message TEXT, "
                + "timestamp TIMESTAMP, "
                + "status VARCHAR(50), "
                + "memberID INT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Payment ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "amount DECIMAL(10, 2), "
                + "date DATE, "
                + "type VARCHAR(50), "
                + "paymentMethod VARCHAR(50), "
                + "description TEXT, "
                + "memberID INT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Workout ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "memberID INT, "
                + "date DATE, "
                + "time TIME, "
                + "preset VARCHAR(255), "
                + "notes TEXT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Stats ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "memberID INT, "
                + "workoutID INT, "
                + "date DATE, "
                + "totalSets INT, "
                + "totalReps INT, "
                + "totalWeight DECIMAL(10, 2), "
                + "duration INT, "
                + "FOREIGN KEY (memberID) REFERENCES Member(id), "
                + "FOREIGN KEY (workoutID) REFERENCES Workout(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS Exercise ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "description TEXT, "
                + "muscleGroups VARCHAR(255), "
                + "equipment VARCHAR(255), "
                + "difficulty INT, "
                + "instructions TEXT, "
                + "sets INT, "
                + "reps INT, "
                + "weight DECIMAL(10, 2)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS ExerciseStats ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "statsID INT, "
                + "exerciseID INT, "
                + "sets INT, "
                + "reps INT, "
                + "weight DECIMAL(10, 2), "
                + "duration INT, "
                + "FOREIGN KEY (statsID) REFERENCES Stats(id), "
                + "FOREIGN KEY (exerciseID) REFERENCES Exercise(id)) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS WorkoutTemplate ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "description TEXT, "
                + "targetMuscles VARCHAR(255), "
                + "repetitions INT, "
                + "exercises TEXT) "
                + "ENGINE=InnoDB;",
                "CREATE TABLE IF NOT EXISTS WorkoutPreset ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "description TEXT, "
                + "targetMuscles VARCHAR(255), "
                + "repetitions INT, "
                + "exercises TEXT) "
                + "ENGINE=InnoDB;"
            };
    
            // Execute each table creation SQL
            for (String sql : createTableSQLs) {
                stmt.executeUpdate(sql);
            }
    
            System.out.println("Tables created (or already exist).");
    
        } catch (SQLException e) {
            System.out.println("An error occurred while creating the database or tables.");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}    
package jazba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2cool4skool";
    
    // Method to fetch exercises from database (unchanged)
    public static List<Exercise> fetchExercisesFromDatabase() {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT name, muscleGroups, description, equipment, difficulty, instructions, sets, reps, weight FROM exercise"; // Fetching relevant columns

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String muscleGroups = resultSet.getString("muscleGroups");
                String description = resultSet.getString("description");
                String equipment = resultSet.getString("equipment");
                int difficulty = resultSet.getInt("difficulty");
                String instructions = resultSet.getString("instructions");
                int sets = resultSet.getInt("sets");
                int reps = resultSet.getInt("reps");
                double weight = resultSet.getDouble("weight");

                // Create Exercise object
                Exercise exercise = new Exercise(name, muscleGroups, description, equipment, difficulty, instructions, sets, reps, weight);
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

    public boolean saveWorkoutPreset(String workoutName, String exerciseName, String targetMuscles, String description, int sets, int reps, double weight) {
        // SQL query to insert a new workout preset, including memberID and workoutName
        String sql = "INSERT INTO workoutpreset (name, description, targetMuscles, repetitions, exercises, memberID) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
    
            String username = UserSession.getLoggedInUserID();
            System.out.println("Username: " + username);
             // Get the logged-in user's username
            int memberID = UserDAO.getUserIDByEmailOrUsername(username);

            // Set parameters for the SQL query
            stmt.setString(1, workoutName);        // Workout Name
            stmt.setString(2, description);        // Workout Description
            stmt.setString(3, targetMuscles);      // Target Muscles
            stmt.setInt(4, reps);           // Repetitions
            stmt.setString(5, exerciseName);          // Exercises (you can store them as a comma-separated list, for example)
            stmt.setInt(6, memberID);              // Member ID (User ID of the logged-in user)
    
            // Execute the query and check if insertion is successful
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

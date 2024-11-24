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

                // Create Exercise object, with memberId set to null
                Exercise exercise = new Exercise(name, muscleGroups, description, equipment, difficulty, instructions, sets, reps, weight);
                exercise.setMemberId(null);  // Set memberId as null
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }
}

package jazba;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LogWorkoutDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/JazbaDB"; // Replace with your DB URL
    private static final String DB_USER = "root";  // Replace with your DB user
    private static final String DB_PASSWORD = "2cool4skool";  // Replace with your DB password

    private Connection connection;

    public LogWorkoutDAO() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void updateExerciseStats(int exerciseId, int sets, int reps, double weight) {
        String sql = "UPDATE exercise SET sets = ?, reps = ?, weight = ? WHERE id = ?";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set the parameters
            statement.setInt(1, sets);
            statement.setInt(2, reps);
            statement.setDouble(3, weight);
            statement.setInt(4, exerciseId);
            
            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Exercise updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Method to save a workout preset
    public int saveWorkoutPreset(WorkoutPreset preset, int memberId) throws SQLException {
        String sql = "INSERT INTO Workout (memberID, date, time, presetID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, memberId);
            statement.setDate(2, Date.valueOf(LocalDate.now()));  // Assuming you want today's date
            statement.setTime(3, Time.valueOf(LocalTime.now()));  // Assuming you want the current time
            statement.setInt(4, preset.getId());

            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // Return the last inserted workout ID
            } else {
                throw new SQLException("Creating workout failed, no ID obtained.");
            }
        }
    }

    // Method to save exercises associated with a workout
    public void saveExerciseStats(int workoutID, Exercise exercise) throws SQLException {
        String sql = "INSERT INTO Stats (exerciseID, sets, reps, weight, workoutID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, exercise.getId());
            statement.setInt(2, exercise.getSets());
            statement.setInt(3, exercise.getReps());
            statement.setDouble(4, exercise.getWeight());
            statement.setInt(5, workoutID);
            
            statement.executeUpdate();
        }
    }

    // Method to retrieve last inserted workout ID for a user
    public int getLastWorkoutID(int memberID) throws SQLException {
        String sql = "SELECT id FROM Workout WHERE memberID = ? ORDER BY date DESC, time DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("No workout found for user.");
            }
        }
    }

    // Close the database connection
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<WorkoutPreset> getWorkoutPresetsByMemberID(int memberID) {
    List<WorkoutPreset> presets = new ArrayList<>();
    String sql = "select * from workoutpreset where memberID = (?)";
    System.out.println("memberID: " + memberID);
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         CallableStatement statement = connection.prepareCall(sql)) {

        statement.setInt(1, memberID);

        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int presetID = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");

                // Retrieve exercises for this workout preset
                List<Exercise> exercises = getExercisesByWorkoutPresetID(presetID);
                System.out.println(presetID);
                
                for (Exercise exercise : exercises) {
                    System.out.println(exercise.getName());
                }
                // Create and add WorkoutPreset to list
                WorkoutPreset preset = new WorkoutPreset(name, description, exercises);
                presets.add(preset);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return presets;
}

public List<Exercise> getExercisesByWorkoutPresetID(int workoutPresetID) throws SQLException {
    String sql = "SELECT * FROM exercise WHERE workout_preset_id = ?";
    List<Exercise> exercises = new ArrayList<>();
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        
        stmt.setInt(1, workoutPresetID);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Exercise exercise = new Exercise(sql, sql, sql, sql, workoutPresetID, sql, workoutPresetID, workoutPresetID, workoutPresetID);
                exercise.setId(rs.getInt("id"));
                exercise.setWorkoutPresetId(rs.getInt("workout_preset_id"));
                exercise.setName(rs.getString("name"));
                exercise.setMuscleGroups(rs.getString("muscleGroups"));
                exercise.setDescription(rs.getString("description"));
                exercise.setEquipment(rs.getString("equipment"));
                exercise.setDifficulty(rs.getInt("difficulty"));
                exercise.setInstructions(rs.getString("instructions"));
                exercise.setSets(rs.getInt("sets"));
                exercise.setReps(rs.getInt("reps"));
                exercise.setWeight(rs.getDouble("weight"));

                exercises.add(exercise);
            }
        }
    }
    return exercises;
}


}

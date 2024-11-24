package jazba;
import java.sql.*;

public class WorkoutPresetDAO {
    private Connection connection;

    public WorkoutPresetDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertWorkoutPreset(WorkoutPreset preset) {
        String query = "INSERT INTO workoutpreset (name, description, targetMuscles, repetitions, exercises, memberID) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, preset.getName());
            stmt.setString(2, preset.getDescription());
            stmt.setString(3, preset.getTargetMuscles());
            stmt.setInt(4, preset.getRepetitions());
            stmt.setString(5, preset.getExercises());
            stmt.setInt(6, preset.getMemberId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

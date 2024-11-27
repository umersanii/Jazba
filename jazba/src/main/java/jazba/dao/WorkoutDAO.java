package jazba.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jazba.models.Exercise;
import jazba.models.Workout;

public class WorkoutDAO {

    public static List<Workout> fetchWorkoutsFromDatabase() {
        // Mock data for demonstration
        Exercise exercise1 = new Exercise("Push-Up", "Chest, Triceps", "Bodyweight push-ups", "None", 1, "Keep back straight", 3, 12, 0);
        Exercise exercise2 = new Exercise("Squat", "Legs, Glutes", "Bodyweight squats", "None", 1, "Keep knees stable", 3, 15, 0);

        Workout workout1 = new Workout("Morning Routine", Arrays.asList(exercise1, exercise2), "A quick workout to start your day");
        Workout workout2 = new Workout("Strength Training", Arrays.asList(exercise1), "Focused on upper body strength");

        return Arrays.asList(workout1, workout2);
    }
}

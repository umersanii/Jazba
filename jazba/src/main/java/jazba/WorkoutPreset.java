package jazba;

import java.util.ArrayList;
import java.util.List;

public class WorkoutPreset {
    private String name;
    private List<Exercise> exercises;

    public WorkoutPreset(String name) {
        this.name = name;
        this.exercises = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }
}

package jazba.models;

import java.util.List;

public class Workout {

    private String name;
    private List<Exercise> exercises;
    private String description;

    public Workout(String name, List<Exercise> exercises, String description) {
        this.name = name;
        this.exercises = exercises;
        this.description = description;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

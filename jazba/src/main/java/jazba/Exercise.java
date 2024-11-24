package jazba;

public class Exercise {
    private final String name;
    private final String targetMuscles;
    private final String description;

    public Exercise(String name, String targetMuscles, String description) {
        this.name = name;
        this.targetMuscles = targetMuscles;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getTargetMuscles() {
        return targetMuscles;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name; // Optional: For debugging
    }
}

package jazba;
public class WorkoutPreset {
    private int id;
    private String name;
    private String description;
    private String targetMuscles;
    private int repetitions;
    private String exercises;
    private int memberId;  // Assuming the member ID is available

    // Constructor
    public WorkoutPreset(String name, String description, String targetMuscles, int repetitions, String exercises, int memberId) {
        this.name = name;
        this.description = description;
        this.targetMuscles = targetMuscles;
        this.repetitions = repetitions;
        this.exercises = exercises;
        this.memberId = memberId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetMuscles() {
        return targetMuscles;
    }

    public void setTargetMuscles(String targetMuscles) {
        this.targetMuscles = targetMuscles;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}

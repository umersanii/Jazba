package jazba;

public class Exercise {

    private String name;
    private String muscleGroups;
    private String description;
    private String equipment;
    private int difficulty;
    private String instructions;
    private int sets;
    private int reps;
    private double weight;
    private Integer memberId;  // MemberId can be null

    public Exercise(String name, String muscleGroups, String description, String equipment, int difficulty, 
                    String instructions, int sets, int reps, double weight) {
        this.name = name;
        this.muscleGroups = muscleGroups;
        this.description = description;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(String muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getTargetMuscles() {
      
        return this.muscleGroups;
    }
}

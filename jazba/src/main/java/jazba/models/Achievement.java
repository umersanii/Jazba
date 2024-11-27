package jazba.models;
import java.time.LocalDate;

public class Achievement {
    private int id;
    private String title;
    private String description;
    private LocalDate dateUnlocked;
    private String badge;
    private int memberID;
    private int unlocked;

    // Constructor
    public Achievement(int id, String title, String description, LocalDate dateUnlocked, String badge, int memberID, int unlocked) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateUnlocked = dateUnlocked;
        this.badge = badge;
        this.memberID = memberID;
        this.unlocked = unlocked;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDateUnlocked() { return dateUnlocked; }
    public String getBadge() { return badge; }
    public int getMemberID() { return memberID; }
    public int getUnlocked() { return unlocked; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDateUnlocked(LocalDate dateUnlocked) { this.dateUnlocked = dateUnlocked; }
    public void setBadge(String badge) { this.badge = badge; }
    public void setMemberID(int memberID) { this.memberID = memberID; }
    public void setUnlocked(int unlocked) { this.unlocked = unlocked; }
}

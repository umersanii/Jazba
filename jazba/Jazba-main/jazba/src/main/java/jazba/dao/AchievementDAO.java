package jazba.dao;

import java.sql.*;
import java.time.LocalDate;

public class AchievementDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "2cool4skool";

    // Main function to check and unlock achievements
    public void checkAndUnlockAchievements(int memberId) {
        String query = "SELECT SUM(sets) AS totalSets, SUM(reps) AS totalReps, SUM(weight) AS totalWeight, COUNT(DISTINCT exerciseName) AS uniqueExercises, COUNT(*) AS totalWorkouts, MAX(date) AS lastWorkoutDate " +
                       "FROM state WHERE memberID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalSets = rs.getInt("totalSets");
                int totalReps = rs.getInt("totalReps");
                double totalWeight = rs.getDouble("totalWeight");
                int uniqueExercises = rs.getInt("uniqueExercises");
                int totalWorkouts = rs.getInt("totalWorkouts");
                String lastWorkoutDate = rs.getString("lastWorkoutDate");

                // Checking achievements based on the current workout stats
                checkFirstStepsAchievement(memberId, totalWorkouts);
                checkConsistencyAchievement(memberId, lastWorkoutDate);
                checkBeastModeAchievement(memberId, totalWeight);
                checkColdSweatAchievement(memberId, totalReps);
                checkIronAddictAchievement(memberId, totalWeight);
                checkMarathonMentalityAchievement(memberId, lastWorkoutDate);
                checkVarietyAchievement(memberId, uniqueExercises);
                checkMilestoneMasterAchievement(memberId, totalWorkouts);
                checkBeastWithinAchievement(memberId);
                checkPowerhouseAchievement(memberId, totalSets);
                checkHallOfFameAchievement(memberId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Achievement checking methods
    private void checkFirstStepsAchievement(int memberId, int totalWorkouts) {
        if (totalWorkouts >= 1 && !isAchievementUnlocked(memberId, "First Steps")) {
            unlockAchievement(memberId, "First Steps");
        }
    }

    private void checkConsistencyAchievement(int memberId, String lastWorkoutDate) {
        if (isConsecutiveWorkouts(memberId, 7) && !isAchievementUnlocked(memberId, "Consistency is Key")) {
            unlockAchievement(memberId, "Consistency is Key");
        }
    }

    private void checkBeastModeAchievement(int memberId, double totalWeight) {
        if (totalWeight >= 5000 && !isAchievementUnlocked(memberId, "Beast Mode")) {
            unlockAchievement(memberId, "Beast Mode");
        }
    }

    private void checkColdSweatAchievement(int memberId, int totalReps) {
        if (totalReps >= 1000 && !isAchievementUnlocked(memberId, "Cold Sweat")) {
            unlockAchievement(memberId, "Cold Sweat");
        }
    }

    private void checkIronAddictAchievement(int memberId, double totalWeight) {
        if (totalWeight >= getBodyWeight(memberId) && !isAchievementUnlocked(memberId, "Iron Addict")) {
            unlockAchievement(memberId, "Iron Addict");
        }
    }

    private void checkMarathonMentalityAchievement(int memberId, String lastWorkoutDate) {
        if (isConsecutiveWorkouts(memberId, 30) && !isAchievementUnlocked(memberId, "Marathon Mentality")) {
            unlockAchievement(memberId, "Marathon Mentality");
        }
    }

    private void checkVarietyAchievement(int memberId, int uniqueExercises) {
        if (uniqueExercises >= 10 && !isAchievementUnlocked(memberId, "Variety is the Spice of Fitness")) {
            unlockAchievement(memberId, "Variety is the Spice of Fitness");
        }
    }

    private void checkMilestoneMasterAchievement(int memberId, int totalWorkouts) {
        if (totalWorkouts >= 100 && !isAchievementUnlocked(memberId, "Milestone Master")) {
            unlockAchievement(memberId, "Milestone Master");
        }
    }

    private void checkBeastWithinAchievement(int memberId) {
        if (hasAchievedBeastWithin(memberId) && !isAchievementUnlocked(memberId, "The Beast Within")) {
            unlockAchievement(memberId, "The Beast Within");
        }
    }

    private void checkPowerhouseAchievement(int memberId, int totalSets) {
        if (totalSets >= 200 && !isAchievementUnlocked(memberId, "Powerhouse")) {
            unlockAchievement(memberId, "Powerhouse");
        }
    }

    private void checkHallOfFameAchievement(int memberId) {
        if (allAchievementsUnlocked(memberId) && !isAchievementUnlocked(memberId, "Hall of Fame")) {
            unlockAchievement(memberId, "Hall of Fame");
        }
    }

    // Helper methods

    // Check if the achievement is unlocked
    public boolean isAchievementUnlocked(int memberId, String achievementName) {
        String query = "SELECT unlocked FROM achievement WHERE memberid = ? AND badge = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            stmt.setString(2, achievementName);
            ResultSet resultSet = stmt.executeQuery();

            return resultSet.next() && resultSet.getBoolean("unlocked");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Unlock the achievement
    private void unlockAchievement(int memberId, String achievementName) {
        String query = "UPDATE achievement SET unlocked = 1 WHERE memberid = ? AND title = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            stmt.setString(2, achievementName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if the user has achieved 30 consecutive workout days (Example, needs date handling logic)
    private boolean isConsecutiveWorkouts(int memberId, int days) {
        String query = "SELECT date FROM state WHERE memberID = ? ORDER BY date DESC LIMIT ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, days);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDate previousDate = LocalDate.parse(rs.getString("date"));
                int consecutiveCount = 1; // Count current day

                while (rs.next()) {
                    LocalDate currentDate = LocalDate.parse(rs.getString("date"));
                    if (currentDate.equals(previousDate.minusDays(1))) {
                        consecutiveCount++;
                    } else {
                        break;
                    }
                    previousDate = currentDate;
                }

                return consecutiveCount >= days;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the body weight of the member (for Iron Addict achievement)
    private double getBodyWeight(int memberId) {
        String query = "SELECT bodyWeight FROM user_profile WHERE memberID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("bodyWeight");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 70.0; // Default weight if not found
    }

    // Check if the user has met the Beast Within achievement criteria (for deadlift, squat, bench press)
    private boolean hasAchievedBeastWithin(int memberId) {
        String query = "SELECT MAX(weight) AS maxWeight FROM state WHERE memberID = ? AND exerciseName IN ('Deadlift', 'Squat', 'Bench Press')";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double maxWeight = rs.getDouble("maxWeight");
                return maxWeight >= 100; // Adjust with actual criteria
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if all achievements are unlocked
    private boolean allAchievementsUnlocked(int memberId) {
        String query = "SELECT COUNT(*) FROM achievement WHERE memberID = ? AND unlocked = 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int unlockedCount = rs.getInt(1);
                return unlockedCount == 11; // Assuming 11 achievements
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void initializeAchievementsForUser(int memberId) {
        // List of default achievements with titles, descriptions, dates, badge references, and unlock status
        Object[][] defaultAchievements = {
            {"First Steps", "Complete your first workout!", "2024-11-27", "badge1", 2, 0},
            {"Consistency is Key", "Log workouts for 7 consecutive days. You're on a roll!", "2024-11-27", "badge2", 2, 1},
            {"Beast Mode", "Lift a total of 5000 kgs across all workouts. Strength unleashed!", "2024-11-27", "badge3", 2, 1},
            {"Cold Sweat", "Log a total of 1000 reps. Keep the heart reps up!", "2024-11-27", "badge4", 2, 1},
            {"Iron Addict", "Bench press your body weight. True dedication!", "2024-11-27", "badge5", 2, 0},
            {"Marathon Mentality", "Work out for 30 consecutive days. A champion in the making!", "2024-11-27", "badge6", 2, 1},
            {"Variety is the Spice of Fitness", "Try 10 different exercises. Explore and conquer!", "2024-11-27", "badge7", 2, 1},
            {"Milestone Master", "Log 100 workouts. Perseverance pays off!", "2024-11-27", "badge8", 2, 1},
            {"The Beast Within", "Achieve Deadlift of 100kg, Squat of 80kg and Bench Press of 60kg. You're a beast!", "2024-11-27", "badge9", 2, 1},
            {"Powerhouse", "Complete a total of 200 sets across all exercises. Power through your workouts!", "2024-11-27", "badge10", 2, 1},
            {"Milestone Master", "Log 100 workouts. Perseverance pays off!", "2024-11-27", "badge11", 2, 1},
            {"Hall of Fame", "Achieve all achievements. The ultimate fitness legend!", "2024-11-27", "badge12", 2, 1}
        };
    
        String insertQuery = "INSERT INTO achievement (memberid, title, description, date, badge, difficulty, unlocked) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            // Set default values for each achievement
            for (Object[] achievement : defaultAchievements) {
                stmt.setInt(1, memberId);  // Set the memberId for the user
                stmt.setString(2, (String) achievement[0]);  // Set the achievement title
                stmt.setString(3, (String) achievement[1]);  // Set the achievement description
                stmt.setDate(4, java.sql.Date.valueOf((String) achievement[2]));  // Set the achievement date
                stmt.setString(5, (String) achievement[3]);  // Set the badge reference
                stmt.setInt(6, (Integer) achievement[4]);  // Set the difficulty
                stmt.setInt(7, (Integer) achievement[5]);  // Set the unlock status
    
                // Execute the insert for each default achievement
                stmt.addBatch();  // Add the statement to batch
            }
    
            // Execute all the inserts in a single batch for better performance
            stmt.executeBatch();
            System.out.println("Default achievements initialized for memberId: " + memberId);
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while initializing achievements for memberId: " + memberId);
        }
    }
    
}


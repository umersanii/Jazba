package jazba;

import java.sql.*;

public class RegistrationDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/JazbaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "2cool4skool";

    // Method to register a new user
    public Integer registerUser(String username, String email, String password, int height, int weight, int age, String fitnessLevel) {
        String memberQuery = "INSERT INTO member (username, email, password, membership_status, registration_date) VALUES (?, ?, ?, false, NOW())";
        String profileQuery = "INSERT INTO profile (height, weight, age, fitnesslevel, memberID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start a transaction
            conn.setAutoCommit(false);

            // Insert data into member table
            try (PreparedStatement memberStmt = conn.prepareStatement(memberQuery, Statement.RETURN_GENERATED_KEYS)) {
                memberStmt.setString(1, username);
                memberStmt.setString(2, email);
                memberStmt.setString(3, password);

                int rowsAffected = memberStmt.executeUpdate();

                if (rowsAffected == 0) {
                    conn.rollback();
                    return -1; // Insertion failed
                }

                // Retrieve the auto-generated memberID from the member table
                ResultSet generatedKeys = memberStmt.getGeneratedKeys();
                int memberID = 0;
                if (generatedKeys.next()) {
                    memberID = generatedKeys.getInt(1);
                }

                // Insert data into profile table
                try (PreparedStatement profileStmt = conn.prepareStatement(profileQuery)) {
                    profileStmt.setInt(1, height);
                    profileStmt.setInt(2, weight);
                    profileStmt.setInt(3, age);
                    profileStmt.setString(4, fitnessLevel);
                    profileStmt.setInt(5, memberID);

                    int profileRowsAffected = profileStmt.executeUpdate();
                    if (profileRowsAffected > 0) {
                        // Commit the transaction
                        conn.commit();
                        return memberID; // Registration successful
                    }
                }

            } catch (SQLException e) {
                conn.rollback(); // Rollback in case of failure
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Registration failed
    }
}

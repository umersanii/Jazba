package jazba;
public class UserSession {
    private static String username;

    // Setter and Getter for logged-in user ID
    public static void setLoggedInUserID(String userID) {
        username = userID;
    }

    public static String getLoggedInUserID() {
        return username;
    }
}


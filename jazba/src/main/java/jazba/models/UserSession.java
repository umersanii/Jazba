package jazba.models;

import jazba.dao.AchievementDAO;
import jazba.dao.UserDAO;

public class UserSession {
    private static int userID;
    private static boolean premiumMember;
    private static String username;
    static UserDAO userDAO = new UserDAO();
    
    // Setter and Getter for logged-in user ID
    public static void setLoggedInUserID(String userID) 
    {
        UserSession.userID = userDAO.getUserIDByEmailOrUsername(userID);
    }  

    public static int getLoggedInUserID() {
        return userID;
        
    }

    public static boolean isPremiumMember() {
        return premiumMember;
    }

    
}


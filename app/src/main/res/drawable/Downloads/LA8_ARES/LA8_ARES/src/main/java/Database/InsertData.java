package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertData {
    public static void insertUser(String username, String password, String fullName) {
        String sql = "INSERT INTO users (username, password, full_name) VALUES (?, ?, ?)";

        try (Connection conn = mySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.executeUpdate();
            System.out.println("LoginPage.User " + username + " inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertUser("jdela_cruz", "pass123", "Juan Dela Cruz");
        insertUser("admin", "admin123", "System Administrator");
    }
}
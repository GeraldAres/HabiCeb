package Database;

import LoginPage.User;
import java.sql.*;

public class retrieveData {
    public static User authenticate(String username, String enteredPassword) {
        // 1. Establish Connection & 2. Use Prepared Statement
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = mySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // 3. Compare entered password with database password
                String dbPassword = rs.getString("password");

                if (dbPassword.equals(enteredPassword)) {
                    // 4. Create a User object upon successful authentication
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            dbPassword // This is transient, so it won't serialize!
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Invalid credentials
    }
}
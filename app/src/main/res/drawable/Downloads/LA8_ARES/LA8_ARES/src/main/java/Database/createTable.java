package Database;

import java.sql.Connection;
import java.sql.Statement;

public class createTable {
    public static void main(String[] args) {
        try (Connection conn = mySQLConnection.getConnection();
             Statement stmt = conn.createStatement()) {


            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) NOT NULL UNIQUE, "
                    + "password VARCHAR(50) NOT NULL, "
                    + "full_name VARCHAR(100) NOT NULL)";
            stmt.execute(sql);
            System.out.println("Table 'users' ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
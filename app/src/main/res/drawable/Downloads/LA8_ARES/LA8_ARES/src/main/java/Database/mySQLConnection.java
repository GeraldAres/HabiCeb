package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/loginares";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        try {
            // Force load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
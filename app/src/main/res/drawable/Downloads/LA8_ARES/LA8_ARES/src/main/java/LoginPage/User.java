package LoginPage;

import java.io.Serializable;
import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String fullName;

    private transient String password; // NOT serialized

    public User() {}

    public User(int userId, String username, String fullName, String password) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getPassword() { return password; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPassword(String password) { this.password = password; }
}
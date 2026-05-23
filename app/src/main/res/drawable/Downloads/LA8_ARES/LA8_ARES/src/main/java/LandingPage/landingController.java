package LandingPage;
import LoginPage.loginApplication;
import LoginPage.loginController;
import LoginPage.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class landingController {
    @FXML private Label welcomeLabel, idLabel, usernameLabel, loginTimeLabel, passwordCheckLabel;

    @FXML
    public void initialize() {
        // 1. Deserialize
        User user = deserializeUser();

        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getFullName());
            idLabel.setText("User ID: " + user.getUserId());
            usernameLabel.setText("Username: " + user.getUsername());

            // 2. Confirm password is null after serialization
            passwordCheckLabel.setText("Current Password (from file): " + user.getPassword());

            // 3. Runtime Session Info
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, yyyy – h:mm a");
            loginTimeLabel.setText("Login Time: " + dtf.format(LocalDateTime.now()));
        }
    }

    private User deserializeUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("currentUser.ser"))) {
            return (User) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    @FXML
    public void handleLogout(javafx.event.ActionEvent event) {
        // REQUIREMENT: Delete file on logout
        loginApplication.deleteSessionFile();

        // Go back to Login Page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package LoginPage;


import Database.InsertData;
import Database.retrieveData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.*;

public class loginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField fullNameField;
    @FXML private Label statusLabel;

    @FXML
    public void handleLogin(javafx.event.ActionEvent event) {

        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullname = fullNameField.getText();

        User user = retrieveData.authenticate(username, password);

        if (user != null) {
            serializeUser(user);
            loadLanding(event);
        } else {
            // Register if full name exists
            if (!fullname.isEmpty()) {
                InsertData.insertUser(username, password, fullname);
                statusLabel.setText("Registered! Please login.");
            } else {
                statusLabel.setText("Invalid credentials!");
            }
        }
    }

    private void serializeUser(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("currentUser.ser"))) {
            oos.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLanding(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LandingPage/landingpage.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
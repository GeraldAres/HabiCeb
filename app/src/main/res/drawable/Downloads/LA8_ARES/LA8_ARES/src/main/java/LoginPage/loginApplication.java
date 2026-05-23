
package LoginPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class loginApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        deleteSessionFile(); // Startup safety

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage/login.fxml"));
        Scene scene = new Scene(loader.load(), 350, 450);

        stage.setTitle("Login System");
        stage.setScene(scene);
        stage.show();
    }

    public static void deleteSessionFile() {
        File file = new File("currentUser.ser");
        if (file.exists()) file.delete();
    }

    public static void main(String[] args) {
        launch();
    }
}
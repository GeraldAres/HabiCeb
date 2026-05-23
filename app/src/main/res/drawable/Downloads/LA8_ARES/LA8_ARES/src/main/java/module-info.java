module com.example.lab4_13_labactivity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens LoginPage to javafx.fxml;
    exports LoginPage;

    opens LandingPage to javafx.fxml;
    exports LandingPage;


    exports Database;
}
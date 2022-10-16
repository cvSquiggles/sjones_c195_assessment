package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Users;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Javadoc files located in sjones_c195_assessment\javadoc
 */
public class Main extends Application {
    /**
     * @param stage stage to render the MainForm.  Parent root will be used throughout the program.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        stage.setTitle("Scheduling Manager v0.1");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    /**
     *
     * @param args ran at start up, no particular arguments passed in. Initializes the Users.currentUser as null.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        Users.currentUser = null;
        launch(args);
        JDBC.closeConnection();
    }

}

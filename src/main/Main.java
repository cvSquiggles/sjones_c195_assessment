package main;


import DBAccess.DBCountries;
import com.mysql.cj.jdbc.JdbcConnection;
import java.sql.*;
import java.text.SimpleDateFormat;

import helper.JDBC;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Countries;
import model.Inventory;
import model.Users;

/**
 * Javadoc files located in C:\School\intelliJ_projects\sjones_c482_assessment\javadoc
 * FUTURE ENHANCEMENT The part/product search functionality could be updated to not be case-sensitive.
 */
public class Main extends Application {
    /**
     *
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
     * @param args ran at start up, no particular arguments passed in. This sets the Inventory.autoIDGen to 2 since parts are auto-enumerated.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Inventory.autoIDGen = 2; //Set autoIDGen for new parts/products to start at 2.
        Users.currentUser = null;
        launch(args);
        JDBC.closeConnection();
    }

}

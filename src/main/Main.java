package main;


import DBAccess.DBCountries;
import com.mysql.cj.jdbc.JdbcConnection;
import java.sql.*;
import helper.JDBC;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Countries;
import model.Inventory;

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
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    /**
     *
     * @param args ran at start up, no particular arguments passed in. This sets the Inventory.autoIDGen to 2 since parts are auto-enumerated.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        System.out.println(countryList.get(2).getName());
        String sql = "SELECT Create_Date FROM countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        Inventory.autoIDGen = 2; //Set autoIDGen for new parts/products to start at 2.
        launch(args);
        JDBC.closeConnection();
    }

}

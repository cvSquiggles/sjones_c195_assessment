package controller;

import helper.AppointmentsQuery;
import helper.CountriesQuery;
import helper.FirstLevelDivisionsQuery;
import helper.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Countries;
import model.FirstLevelDivisions;
import model.Users;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LogInFormController implements Initializable {

    public Label timeZoneLabel;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button logInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get currentUserData
        Users.homePageLoaded = false;
        Users.currentUserLocale = Locale.getDefault();
        Users.currentUserZoneID = TimeZone.getDefault().toZoneId();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(Users.currentUserZoneID);
        Users.currentUserTimeZone = zonedDateTime.getOffset();
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set language package
        Users.currentUserRB = ResourceBundle.getBundle("properties/Nat", Users.currentUserLocale);

        //Populate Countries
        try {
            Countries.countryOptions = CountriesQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate First-Level Divisions Data
        try {
            FirstLevelDivisions.divisionOptions = FirstLevelDivisionsQuery.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param actionEvent When log in button is clicked, verify credentials, and proceed to log in as user if they are correct.
     * @throws IOException
     * @throws SQLException
     */
    public void onActionLogInButton(ActionEvent actionEvent) throws IOException, SQLException {

        String currentUser = null;
        int credentialCheck = UsersQuery.signIn(usernameTextField.getText(), passwordTextField.getText());
        Path path;

        switch (credentialCheck) {
            case 0:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log in failed.");
                alert.setContentText("Invalid credentials entered.");
                alert.show();
                //Write path code!!!!
                path = Paths.get("login_activity.txt");
                System.out.println(Files.exists(path));
                if(!Files.exists(path)){
                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try{
                        Files.writeString(path, "FAILED | Username: " + usernameTextField.getText() + " | Timestamp: " + LocalDateTime.now(),
                                StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else{
                    try{
                        Files.writeString(path, "\nFAILED | Username: " + usernameTextField.getText() + " | Timestamp: " + LocalDateTime.now(),
                                StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                usernameTextField.setText("");
                passwordTextField.setText("");
                break;
            case 1:
                //Write path code!!!!
                path = Paths.get("login_activity.txt");
                System.out.println(Files.exists(path));
                if(!Files.exists(path)){
                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try{
                        Files.writeString(path, "SUCCESS | Username: " + usernameTextField.getText() + " | Timestamp: " + LocalDateTime.now(),
                                StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else{
                    try{
                        Files.writeString(path, "\nSUCCESS | Username: " + usernameTextField.getText() + " | Timestamp: " + LocalDateTime.now(),
                                StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200.0, 600.0);
                stage.setTitle("Home Page");
                stage.setScene(scene);
                stage.show();
                break;
            default:
                Alert alertDefault = new Alert(Alert.AlertType.ERROR);
                alertDefault.setTitle("Log in failed.");
                alertDefault.setContentText("An unknown error has occurred..");
                alertDefault.show();
                usernameTextField.setText("");
                passwordTextField.setText("");
        }

        /*int qCheck = AppointmentsQuery.insert("Title", "The description", "Place", "Cult", currentUser, 2, 1, 2);
        if (qCheck != 0) {
            System.out.println("WE put IT in!");
        }*/

        /*int qCheck = AppointmentsQuery.update("Final Title?", "The description", "Place", "Cult", 2, 1, 2, 7);
        if (qCheck != 0) {
            System.out.println("WE altered it!");4
        }*/

        /*int qCheck = AppointmentsQuery.delete(7);
        if (qCheck != 0) {
            System.out.println("WE deleted it!");
        }*/

        /*ResultSet rs = AppointmentsQuery.select(2);
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp created = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            String customer = rs.getString("Customer_Name");
            String userName = rs.getString("User_Name");
            String contactName = rs.getString("Contact_Name");
        }*/


    }
}

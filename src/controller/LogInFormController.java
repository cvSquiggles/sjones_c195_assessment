package controller;

import helper.CountriesQuery;
import helper.FirstLevelDivisionsQuery;
import helper.UsersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;
import model.Users;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LogInFormController implements Initializable {

    public Label timeZoneLabel;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button logInButton;

    /**
     *
     * @param url
     * @param resourceBundle
     * Populate the Login form and gather current user timezone/zoneID, country/divisions lists, etc.
     */
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
     * @param actionEvent When log in button is clicked, verify credentials, and proceed to log in as user if they are valid, then navigate to HomePageForm.
     * @throws IOException
     * @throws SQLException
     * Passes the username and password into the UsersQuery signIn function to determine credential validity.
     * Writes results of login attempt to 'login_activity.txt' file in the root directory.
     */
    public void onActionLogInButton(ActionEvent actionEvent) throws IOException, SQLException {

        //pass the username and password into the UsersQuery signIn function to determine credential validity.
        String currentUser = null;
        int credentialCheck = UsersQuery.signIn(usernameTextField.getText(), passwordTextField.getText());
        Path path;

        //Switch to evaluate the result of the signIn() attempt.
        switch (credentialCheck) {
            case 0:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log in failed.");
                alert.setContentText("Invalid credentials entered.");
                alert.show();
                path = Paths.get("login_activity.txt");
                System.out.println(Files.exists(path));
                //Check if login_activity.txt exists, and populate form accordingly.
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
                //Check if login_activity.txt exists, and populate form accordingly.
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
                //Load HomePageForm
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

    }
}

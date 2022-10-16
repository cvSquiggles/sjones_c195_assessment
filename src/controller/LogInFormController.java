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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;
import model.Users;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controls the LogInForm.fxml
 * On initialization, grabs information about the users' location/time zone, as well as the proper resource bundle to use for them.
 * Pulls form data and submits to database as a login attempt via UsersQuery.signIn(), and logs this attempt to the login_activity.txt file in the root directory
 */
public class LogInFormController implements Initializable {

    public Label timeZoneLabel;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button logInButton;
    public Label headerLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label locHeader;

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
        //Users.currentUserZoneID = TimeZone.getDefault().toZoneId();
        Users.currentUserZoneID = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(Users.currentUserZoneID);
        Users.currentUserTimeZone = zonedDateTime.getOffset();
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Check user language, default to english if not supported
        if(!(Users.currentUserLocale.getLanguage().equals("fr") || Users.currentUserLocale.getLanguage().equals("en"))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Language not supported!");
            alert.setContentText("Language defaulted to English");
            alert.show();

            //Set language package
            Users.currentUserRB = ResourceBundle.getBundle("properties/Nat_" + "en");
        }else{
            //Set language package
            Users.currentUserRB = ResourceBundle.getBundle("properties/Nat_" + Users.currentUserLocale.getLanguage());
        }
        //Update UI
        headerLabel.setText(Users.currentUserRB.getString("Welcome") + " " + Users.currentUserRB.getString("to") + " C195 " +
                Users.currentUserRB.getString("Schedule") + " " + Users.currentUserRB.getString("Assistant") + "!");
        usernameLabel.setText(Users.currentUserRB.getString("Username") + ":");
        passwordLabel.setText(Users.currentUserRB.getString("Password") + ":");
        usernameTextField.setPromptText(Users.currentUserRB.getString("Enter") + " " + Users.currentUserRB.getString("username") + "...");
        passwordTextField.setPromptText(Users.currentUserRB.getString("Enter") + " " + Users.currentUserRB.getString("password") + "...");
        locHeader.setText(Users.currentUserRB.getString("Location"));

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
     * Write results of login attempt to 'login_activity.txt' file in the root directory.
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
                alert.setTitle(Users.currentUserRB.getString("Login")  + " " + Users.currentUserRB.getString("failed"));
                alert.setContentText(Users.currentUserRB.getString("Invalid") + " " + Users.currentUserRB.getString("credentials") + ".");
                alert.show();
                path = Paths.get("login_activity.txt");
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
                alertDefault.setTitle(Users.currentUserRB.getString("Login")  + " " + Users.currentUserRB.getString("failed"));
                alertDefault.setContentText(Users.currentUserRB.getString("An") + " " +
                        Users.currentUserRB.getString("unknown") + " " + Users.currentUserRB.getString("error")
                        + " " + Users.currentUserRB.getString("has") + " " + Users.currentUserRB.getString("occurred") + "...");
                alertDefault.show();
                usernameTextField.setText("");
                passwordTextField.setText("");
        }

    }
}

package controller;

import helper.AppointmentsQuery;
import helper.UsersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale myLocale = Locale.getDefault();
        ZoneId myTimeZoneID = TimeZone.getDefault().toZoneId();
        timeZoneLabel.setText(myTimeZoneID.toString());
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

        switch (credentialCheck) {
            case 0:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log in failed.");
                alert.setContentText("Invalid credentials entered.");
                alert.show();
                break;
            case 1:
                System.out.println("This is correct!");
                currentUser = usernameTextField.getText();
                break;
            default:
                System.out.println("An unknown error has occurred.");
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

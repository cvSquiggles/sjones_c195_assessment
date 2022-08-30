package controller;

import helper.AppointmentsQuery;
import helper.UsersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Users;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class HomePageFormController implements Initializable{

    public Button quitButton;
    public Button signOutButton;
    public Button appointmentsButton;
    public Button customersButton;
    public Label welcomeUserLabel;
    public Label timeZoneLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone
        Locale myLocale = Locale.getDefault();
        ZoneId myTimeZoneID = TimeZone.getDefault().toZoneId();
        timeZoneLabel.setText(myTimeZoneID.toString());

        //Set username display
        welcomeUserLabel.setText("Welcome " + Users.currentUser + " | ");
    }

    public void onActionSignOutButton(ActionEvent actionEvent) {
    }

    public void onActionQuitButton(ActionEvent actionEvent) {
    }

    public void onActionCustomersButton(ActionEvent actionEvent) {
    }

    public void onActionAppointmentsButton(ActionEvent actionEvent) {
    }

    public void onActionWelcomeUserLabel(MouseEvent mouseEvent) {
    }
}

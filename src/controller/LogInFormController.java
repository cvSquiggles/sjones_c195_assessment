package controller;

import helper.UsersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale myLocale = Locale.getDefault();
        ZoneId myTimeZoneID = TimeZone.getDefault().toZoneId();
        timeZoneLabel.setText(myTimeZoneID.toString());
    }

    public void onActionLogInButton(ActionEvent actionEvent) throws IOException, SQLException {

        int credentialCheck = UsersQuery.signIn(usernameTextField.getText(), passwordTextField.getText());

        if(credentialCheck == 1) {
            System.out.println("This is correct!");
        }
        else{
            System.out.println("This is incorrect!");
        }
    }
}

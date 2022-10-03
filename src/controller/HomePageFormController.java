package controller;

import helper.AppointmentsQuery;
import helper.JDBC;
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
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomePageFormController implements Initializable{

    public Button quitButton;
    public Button signOutButton;
    public Button appointmentsButton;
    public Button customersButton;
    public Label welcomeUserLabel;
    public Label timeZoneLabel;
    public Button aapmntTotalsButton;
    public Button appmntContactButton;
    public Button instAppmntButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        welcomeUserLabel.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        if (!Users.homePageLoaded){
            ObservableList<Appointments> appsToCheck = FXCollections.observableArrayList();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime startStamp = LocalDateTime.now(Users.currentUserZoneID);

            String startDate = startStamp.toString().substring(0, 10);
            System.out.println(startDate);
            String startTime = startStamp.toString().substring(11, 19);
            System.out.println(startTime);

            String startStampConverted = startDate + " " + startTime;
            LocalDateTime start = LocalDateTime.parse(startStampConverted, formatter);
            System.out.println(start);

            try {
                ResultSet rs = AppointmentsQuery.selectByUserWithTime(Users.currentUser.getId(), Users.currentUserTimeZone.toString(), startStampConverted);
                if(rs.next()){
                    System.out.println(rs.getString("Title"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionQuitButton(ActionEvent actionEvent) {
        //Exit the application
        JDBC.closeConnection();
        System.exit(0);
    }

    public void onActionCustomersButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsViewForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void appmntTotalsButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppointmentTotalsForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointment Totals Report");
        stage.setScene(scene);
        stage.show();
    }

    public void appmentContactButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppointmentContactsForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointment Totals Report");
        stage.setScene(scene);
        stage.show();
    }

    public void instAppmentButtonOnAction(ActionEvent actionEvent) {
    }
}

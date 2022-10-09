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
    public Button testingAppmntButton;
    private ResourceBundle rb = Users.currentUserRB;


    /**
     * Populate the UI, and display log-in appointment check pop-up if it's the users first time viewing the page since logging in
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        welcomeUserLabel.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        //Check to see if it's the users first time logging in by reading the bool that is flipped on the Users class.
        if (!Users.homePageLoaded){
            //Check the appointments in the database for any meetings within the next 15 minutes for the current user
            //and display it in the alert if so.
            ObservableList<Appointments> appsToCheck = FXCollections.observableArrayList();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime startStamp = LocalDateTime.now(Users.currentUserZoneID);

            String startDate = startStamp.toString().substring(0, 10);
            String startTime = startStamp.toString().substring(11, 19);

            String startStampConverted = startDate + " " + startTime;
            LocalDateTime start = LocalDateTime.parse(startStampConverted, formatter);

            try {
                ResultSet rs = AppointmentsQuery.selectByUserWithTime(Users.currentUser.getId(), Users.currentUserTimeZone.toString(), startStampConverted);
                if(rs.next()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle( rb.getString("Upcoming") + " " + rb.getString("appointment") + "!");
                    alert.setContentText("ID: " + rs.getInt("Appointment_ID") +  " | " + rb.getString("Date") + "/" +
                            rb.getString("Time") + ": " + rs.getString("Start"));
                    alert.show();
                    Users.homePageLoaded = true;
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle( rb.getString("No") + " " + rb.getString("upcoming") + " " +
                            rb.getString("appointments") + "!");
                    alert.setContentText( rb.getString("There") + " " + rb.getString("are") + " " + rb.getString("no") + " " +
                            rb.getString("appointments") + " " + rb.getString("scheduled") + " " + rb.getString("for") + " " +
                            rb.getString("you") + " " + rb.getString("within") + " " + rb.getString("the") + " " +
                            rb.getString("next") + " 15 " +  rb.getString("minutes") + "!");
                    alert.show();
                    Users.homePageLoaded = true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * @param actionEvent clear currentUser data, and set homePageLoaded to false again, then return to the login form
     * @throws IOException
     */
    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;
        Users.homePageLoaded = false;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Close database connection out, and exit the application
     */
    public void onActionQuitButton(ActionEvent actionEvent) {
        //Exit the application
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     *
     * @param actionEvent Load the CustomersViewForm
     * @throws IOException
     */
    public void onActionCustomersButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Load the AppointmentsViewForm
     * @throws IOException
     */
    public void onActionAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsViewForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Load the ReportAppointmentTotalsForm
     * @throws IOException
     */
    public void appmntTotalsButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppointmentTotalsForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointment Totals Report");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Load the ReportAppointmentContactsForm
     * @throws IOException
     */
    public void appmentContactButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppointmentContactsForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointment Totals Report");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Load the ReportAppointmentTestingForm
     * @throws IOException
     */
    public void testingAppmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppointmentTestingForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Testing Log Report");
        stage.setScene(scene);
        stage.show();
    }
}

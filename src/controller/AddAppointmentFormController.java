package controller;

import helper.*;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointmentFormController implements Initializable {

    public Label welcomeUserLabel;
    public Label timeZoneLabel;
    public Button cancelButton;
    public Button createButton;
    public Button homeButton;
    public Button signOutButton;
    public DatePicker startDatePicker;
    public ChoiceBox hourChoiceBox;
    public ChoiceBox ampmChoiceBox;
    public ChoiceBox minuteChoiceBox;
    public ComboBox customerComboBox;
    public ChoiceBox endampmChoiceBox;
    public ChoiceBox endMinuteChoiceBox;
    public ChoiceBox endHourChoiceBox;
    public DatePicker endDatePicker;
    public ComboBox contactComboBox;
    public TextField locationTextField;
    public TextField descriptionTextField;
    public TextField titleTextField;
    public TextField typeTextField;
    public ComboBox userComboBox;
    private ResourceBundle rb = Users.currentUserRB;
    private ObservableList<Users> userList = FXCollections.observableArrayList();

    /**
     * Populate UI, as well as combo box options.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        welcomeUserLabel.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        //Populate user comboBox
        try {
            Users.userOptions = UsersQuery.selectUsers();
            int i = 0;
            while(i < Users.userOptions.size()){
                userList.add(Users.userOptions.get(i));
                i++;
            }
            i = 0;
            while (i < userList.size()){
                userComboBox.getItems().add(userList.get(i).getUserName());
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate customer comboBox
        ObservableList<String> customerList = FXCollections.observableArrayList();
        try {
            Customers.customerOptions = CustomersQuery.selectCustomerList();
            int i = 0;
            while(i < Customers.customerOptions.size()){
                customerList.add(Customers.customerOptions.get(i).getName());
                i++;
            }
            customerComboBox.setItems(customerList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //Populate contact comboBox
        ObservableList<String> contactList = FXCollections.observableArrayList();
        try {
            Contacts.contactOptions = ContactsQuery.selectContacts();
            int i = 0;
            while(i < Contacts.contactOptions.size()){
                contactList.add(Contacts.contactOptions.get(i).getName());
                i++;
                }
            contactComboBox.setItems(contactList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate start time choice boxes and highlight default values.
        int i = 1;
        while (i < 13) {
            hourChoiceBox.getItems().add(i);
            i++;
        }
        hourChoiceBox.getSelectionModel().selectFirst();
        i = 0;
        while (i < 60) {
            if(i < 10) {
                minuteChoiceBox.getItems().add("0" + i);
            }
            else {
                minuteChoiceBox.getItems().add(i);
            }
            i++;
        }
        minuteChoiceBox.getSelectionModel().selectFirst();
        ampmChoiceBox.getItems().add("AM");
        ampmChoiceBox.getItems().add("PM");
        ampmChoiceBox.getSelectionModel().selectFirst();

        //Populate end time choice boxes and highlight default values.
        i = 1;
        while (i < 13) {
            endHourChoiceBox.getItems().add(i);
            i++;
        }
        endHourChoiceBox.getSelectionModel().selectFirst();
        i = 0;
        while (i < 60) {
            if(i < 10) {
                endMinuteChoiceBox.getItems().add("0" + i);
            }
            else {
                endMinuteChoiceBox.getItems().add(i);
            }
            i++;
        }

        endMinuteChoiceBox.getSelectionModel().selectFirst();
        endampmChoiceBox.getItems().add("AM");
        endampmChoiceBox.getItems().add("PM");
        endampmChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     *
     * @param actionEvent clear currentUser data, and set homePageLoaded to false again, then return to the login form.
     * @throws IOException
     */
    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;
        Users.homePageLoaded = true;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent return to the HomePageForm
     * @throws IOException
     */
    public void onActionHomeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Home Page");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Return to the AppointmentsViewForm
     * @throws IOException
     */
    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointments Page");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent Verify essential information was filled out properly, then submit it to the SQL database as a new appointment
     * @throws SQLException
     * @throws IOException
     */
    public void onActionCreateButton(ActionEvent actionEvent) throws SQLException, IOException {
        //Verify that a customer, contact and user are selected
        try {
            int customerID = Customers.customerOptions.get(customerComboBox.getSelectionModel().getSelectedIndex()).getID();
            int contactID = Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID();
            int assignedUserID = Users.userOptions.get(userComboBox.getSelectionModel().getSelectedIndex()).getId();
        }
        catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("Customer") + "/" + rb.getString("Contact") + "/" + rb.getString("User") +
                    rb.getString("Selection") + " " + rb.getString("Error"));
            alert.setContentText( rb.getString("You") + " " + rb.getString("must") + " " + rb.getString("select") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " + rb.getString("customer") + ", " +
                    rb.getString("user") + ", " + rb.getString("and") + " " + rb.getString("contact") + ".");
            alert.show();
            return;
        }
        //Verify that a title was entered
        if(titleTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Title") + " " + rb.getString("entry") + " " + rb.getString("error") + " " + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " + rb.getString("title") + ".");
            alert.show();
            return;
        }
        //Verify that a description was entered
        if(descriptionTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Description") + " " + rb.getString("entry") + " " + rb.getString("error") + " " + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " + rb.getString("a") + " " +
                    rb.getString("valid") + " " + rb.getString("description") + ".");
            alert.show();
            return;
        }
        //Verify that a location was entered
        if(locationTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Location") + " " + rb.getString("entry") + " " + rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " + rb.getString("location") + ".");
            alert.show();
            return;
        }
        //Verify that a type was entered
        if(typeTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Type") + " " + rb.getString("entry") + " " + rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " + rb.getString("a") + " " +
                    rb.getString("valid") + " " + rb.getString("type") + ".");
            alert.show();
            return;
        }

        if(startDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Date") + " " + rb.getString("entry") + " " + rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " + rb.getString("a") + " " +
                    rb.getString("valid") + " " + rb.getString("date") + ".");
            alert.show();
            return;
        }

        if(endDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Date") + " " + rb.getString("entry") + " " + rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " + rb.getString("a") + " " +
                    rb.getString("valid") + " " + rb.getString("date") + ".");
            alert.show();
            return;
        }

        //Get form data
        //Get the ID of the selected customer, user, and contact
        int customerID = Customers.customerOptions.get(customerComboBox.getSelectionModel().getSelectedIndex()).getID();
        int contactID = Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID();
        int assignedUserID = Users.userOptions.get(userComboBox.getSelectionModel().getSelectedIndex()).getId();
        //Initialize variables to capture form data
        String startDateTime = "";
        String endDateTime = "";
        String timeZoneOffset = Users.currentUserTimeZone.toString();
        String currentUser = Users.currentUser.getUserName();
        String title = titleTextField.getText();
        String desc = descriptionTextField.getText();
        String loc = locationTextField.getText();
        String type = typeTextField.getText();
        String startDayFormat = "-"; //String to adjust startDayFormat, updated in the case that it's the 1st-9th
        String endDayFormat = "-"; //String to adjust endDayFormat, updated in the case that it's the 1st-9th

        //ensure month is double-digit for date/time formatter checks ahead as well as day
        if(startDatePicker.getValue().getDayOfMonth() < 10){
            startDayFormat = "-0";
        }
        if (startDatePicker.getValue().getMonthValue() < 10) {
            //Get start date/time stamp by breaking down the information populated in the ampm, minute, hour, and date selector form fields.
            if (ampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
                startDateTime = (startDatePicker.getValue().getYear() + "-0" + startDatePicker.getValue().getMonthValue() +
                        startDayFormat + startDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                        ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            } else {
                startDateTime = (startDatePicker.getValue().getYear() + "-0" + startDatePicker.getValue().getMonthValue() +
                        startDayFormat + startDatePicker.getValue().getDayOfMonth() + " " + hourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                        ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            }
        } else{
            //Get start date/time stamp by breaking down the information populated in the ampm, minute, hour, and date selector form fields.
            if (ampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
                startDateTime = (startDatePicker.getValue().getYear() + "-" + startDatePicker.getValue().getMonthValue() +
                        startDayFormat + startDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                        ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            } else {
                startDateTime = (startDatePicker.getValue().getYear() + "-" + startDatePicker.getValue().getMonthValue() +
                        startDayFormat + startDatePicker.getValue().getDayOfMonth() + " " + hourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                        ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            }
        }

        //ensure month is double value is double-digit for date/time formatter checks ahead
        if(endDatePicker.getValue().getDayOfMonth() < 10){
            endDayFormat = "-0";
        }
        if (endDatePicker.getValue().getMonthValue() < 10) {
            //Get end date/time stamp by breaking down the information populated in the ampm, minute, hour, and date selector form fields.
            if (endampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(endHourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
                endDateTime = (endDatePicker.getValue().getYear() + "-0" + endDatePicker.getValue().getMonthValue() +
                        endDayFormat + endDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(endHourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                        ":" + endMinuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            } else {
                endDateTime = (endDatePicker.getValue().getYear() + "-0" + endDatePicker.getValue().getMonthValue() +
                        endDayFormat + endDatePicker.getValue().getDayOfMonth() + " " + endHourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                        ":" + endMinuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            }
        } else {
            if (endampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(endHourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
                endDateTime = (endDatePicker.getValue().getYear() + "-" + endDatePicker.getValue().getMonthValue() +
                        endDayFormat + endDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(endHourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                        ":" + endMinuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            } else {
                endDateTime = (endDatePicker.getValue().getYear() + "-" + endDatePicker.getValue().getMonthValue() +
                        endDayFormat + endDatePicker.getValue().getDayOfMonth() + " " + endHourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                        ":" + endMinuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            }
        }

        //Query to verify date/times are valid within constraints
        //To learn TIME_DIFF/TIMESTAMPDIFF, referenced https://stackoverflow.com/questions/4759248/difference-between-two-dates-in-mysql and,
        //https://www.w3schools.com/sql/func_mysql_timediff.asp
        String sql = "SELECT CONVERT_TZ(?, ?, '-5:00') as Start, CONVERT_TZ(?, ?, '-5:00') as End, TIMEDIFF(?, ?) as Diff, " +
                "TIMESTAMPDIFF(SECOND, ?, ?) as DiffSeconds";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, startDateTime);
        ps.setString(2, Users.currentUserTimeZone.toString());
        ps.setString(3, endDateTime);
        ps.setString(4, Users.currentUserTimeZone.toString());
        ps.setString(5, endDateTime);
        ps.setString(6, startDateTime);
        ps.setString(7, startDateTime);
        ps.setString(8, endDateTime);


        ResultSet rs = ps.executeQuery();
        rs.next();
        String startConverted = rs.getString("Start");
        String endConverted = rs.getString("End");
        String diff = rs.getString("Diff");
        int diffSeconds = rs.getInt("DiffSeconds");

        //Pull values from the start/end times queried to compare against
        int startConvertedValue = Integer.parseInt(startConverted.substring((startConverted.indexOf(':')-2), (startConverted.indexOf(':'))));
        int endConvertedValue = Integer.parseInt(endConverted.substring((endConverted.indexOf(':')-2), (endConverted.indexOf(':'))));
        int endConvertedValueMin = Integer.parseInt(endConverted.substring((endConverted.indexOf(':') + 1), (endConverted.indexOf(':') + 3)));
        int diffInHours = Integer.parseInt(diff.substring((diff.indexOf(':') - 2), (diff.indexOf(':'))));

        //If start is after end, fail and alert user
        if (diffSeconds < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Meeting") + " " + rb.getString("time") + " " +
                    rb.getString("logic") + " " + rb.getString("error"));
            alert.setContentText( rb.getString("Please") + " " + rb.getString("schedule") + " " + rb.getString("meeting") + " " +
                    rb.getString("to") + " " + rb.getString("end") + " " + rb.getString("after") + " " +
                    rb.getString("it") + rb.getString("Starts") + ".");
            alert.show();
            return;
        }

        //If Meeting is longer than business hours allow, fail and alert user
        if (diffInHours >= 14){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Meeting") + " " + rb.getString("is") + " " + rb.getString("longer") + " " +
                    rb.getString("than") + " " + rb.getString("business") + " " + rb.getString("hours") + " " +
                    rb.getString("allow") + ".");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("reduce") + " " + rb.getString("meeting") + " " +
                    rb.getString("length") + " " + rb.getString("to") + " " + rb.getString("remain") + " " +
                    rb.getString("within") + " " + rb.getString("a") + " " + "14" + " " + rb.getString("hour") + " " +
                    rb.getString("business") + " " + rb.getString("day") + ".");
            alert.show();
            return;
        }

        //If meeting starts before 8am EST, or after 10pm EST, fail and alert user
        if (startConvertedValue < 8 || startConvertedValue >= 22){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Meeting") + " " + rb.getString("starts") + " " +
                    rb.getString("outside") + " " + rb.getString("of") + " " + rb.getString("business") + " " +
                    rb.getString("hours") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("schedule") + " " + rb.getString("meeting") + " " +
                    rb.getString("to") + " " + rb.getString("start") + " " + rb.getString("after") + " " +
                    "8am EST" + ", " + rb.getString("and") + " " + rb.getString("before") + " 10pm EST.");
            alert.show();
            return;
        }

        //If meeting ends after 10pm EST, fail and alert user
        if (endConvertedValue < 8 || (endConvertedValue >= 22 && endConvertedValueMin > 0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Meeting") + " " + rb.getString("ends") + " " +
                    rb.getString("outside") + " " + rb.getString("of") + " " + rb.getString("business") + " " +
                    rb.getString("hours") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("schedule") + " " + rb.getString("meeting") + " " +
                    rb.getString("to") + " " + rb.getString("end") + " " + rb.getString("before") + " 10pm EST.");
            alert.show();
            return;
        }

        //Code to check if a start or end time overlaps with a customers other meetings.
        ObservableList<Appointments> appsToCheck = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
        //Query for the selected customer's meetings already in the database, then compare them against the new meetings date/times.
        try {
            ResultSet rs2 = AppointmentsQuery.selectByCustomer(2, Users.currentUserTimeZone.toString());
            while(rs2.next()){
                Appointments appToAdd = new Appointments(rs2.getInt("Appointment_ID"), rs2.getString("Title"), rs2.getString("Description"),
                        rs2.getString("Location"), rs2.getString("Contact_Name"), rs2.getString("Type"), rs2.getString ("Created_By"),
                        rs2.getString("Start"), rs2.getString("End"), rs2.getInt("Customer_ID"), rs2.getInt("User_ID"),
                        rs2.getInt("Contact_ID"), rs2.getString("Customer_Name"));

                appsToCheck.add(appToAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int i = 0;
        while (i < appsToCheck.size()){
            if (start.isAfter(appsToCheck.get(i).getStartStamp()) && start.isBefore(appsToCheck.get(i).getEndStamp())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle( rb.getString("Meeting") + " " + rb.getString("conflict"));
                alert.setContentText( rb.getString("This") + " " + rb.getString("meeting") + " " + rb.getString("starts") + " " +
                        rb.getString("during") + " " + rb.getString("another") + " " + rb.getString("of") + " " +
                        rb.getString("this") + " " + rb.getString("clients'") + " " + rb.getString("meetings") + " " +
                        rb.getString("titled") + appsToCheck.get(i).getTitle() + "!");
                alert.show();
                return;
            }
            if (end.isAfter(appsToCheck.get(i).getStartStamp()) && end.isBefore(appsToCheck.get(i).getEndStamp())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Meeting") + " " + rb.getString("conflict"));
                alert.setContentText(rb.getString("This") + " " + rb.getString("meeting") + " " + rb.getString("starts") + " " +
                        rb.getString("during") + " " + rb.getString("another") + " " + rb.getString("of") + " " +
                        rb.getString("this") + " " + rb.getString("clients'") + " " + rb.getString("meetings") + " " +
                        rb.getString("titled") + appsToCheck.get(i).getTitle() + "!");
                alert.show();
                return;
            }
            i++;
        }
        //After passing checks, run AppointmentsQuery.insert, and either load the AppointmentsViewForm, or display an error if the appointment wasn't created successfully.
        if (AppointmentsQuery.insert(title, desc, loc,type, startDateTime, endDateTime, timeZoneOffset, Users.currentUser.getUserName(), customerID,
                assignedUserID, contactID) != 0){

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsViewForm.fxml"));

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1200.0, 600.0);
            stage.setTitle("Appointments page");

            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Unable") + " " + rb.getString("to") + " " + rb.getString("add") + " " +
                    rb.getString("appointment") + " " + rb.getString("with") + " " + rb.getString("provided") + " " +
                    rb.getString("information") + "!");
            alert.setContentText( rb.getString("Something") + " " + rb.getString("must") + " " + rb.getString("be") + " " + rb.getString("wrong") + ".");
            alert.show();
        }
    }
}

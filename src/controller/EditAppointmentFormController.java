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
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditAppointmentFormController implements Initializable {
    public Label welcomeUserLabel;
    public Label timeZoneLabel;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public ComboBox contactComboBox;
    public DatePicker endDatePicker;
    public ChoiceBox endHourChoiceBox;
    public ChoiceBox endMinuteChoiceBox;
    public ChoiceBox endampmChoiceBox;
    public ComboBox customerComboBox;
    public DatePicker startDatePicker;
    public ChoiceBox hourChoiceBox;
    public ChoiceBox minuteChoiceBox;
    public ChoiceBox ampmChoiceBox;
    public TextField typeTextField;
    public Button signOutButton;
    public Button homeButton;
    public Button cancelButton;
    public Button createButton;
    private static Appointments appointmentToEdit = null; //Appointments Object to allow passing in an Appointments object from another form
    public ComboBox userComboBox;

    public static void appointmentToEdit(Appointments passAppointment) {appointmentToEdit = passAppointment;} //Functionality to pass in Appointments
    private ObservableList<Customers> customerList = FXCollections.observableArrayList();
    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    private ObservableList<Users> userList = FXCollections.observableArrayList();

    /**
     * Populate UI, as well as form options, and set form fields to those passed in with the appointmentToEdit method
     * @param url
     * @param resourceBundle
     */
    @Override
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
        try {
            Customers.customerOptions = CustomersQuery.selectCustomerList();
            int i = 0;
            while(i < Customers.customerOptions.size()){
                customerList.add(Customers.customerOptions.get(i));
                i++;
            }
            i = 0;
            while(i < customerList.size()){
                customerComboBox.getItems().add(customerList.get(i).getName());
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate contact combo box
        try {
            Contacts.contactOptions = ContactsQuery.selectContacts();
            int i = 0;
            while(i < Contacts.contactOptions.size()){
                contactList.add(Contacts.contactOptions.get(i));
                i++;
            }
            i = 0;
            while(i < contactList.size()){
                contactComboBox.getItems().add(contactList.get(i).getName());
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate text fields
        titleTextField.setText(appointmentToEdit.getTitle());
        descriptionTextField.setText(appointmentToEdit.getDescription());
        locationTextField.setText(appointmentToEdit.getLocation());
        typeTextField.setText(appointmentToEdit.getType());
        //Iterate through combo boxes and select the proper options based on index
        int i = 0;

        while (i < userComboBox.getItems().size()){
            if(userList.get(i).getId() == appointmentToEdit.getUserID()){
                userComboBox.setValue(userList.get(i).getUserName());
                i = userComboBox.getItems().size();
            }
            else{
                i++;
            }
        }

        i = 0;
        while (i < contactComboBox.getItems().size()){
            if(contactComboBox.getItems().get(i).toString().contentEquals(appointmentToEdit.getContactName())){
                contactComboBox.setValue(contactComboBox.getItems().get(i));
                i = contactComboBox.getItems().size();
            }else {
                i++;
            }
        }

        i = 0;
        while (i < customerComboBox.getItems().size()){
            if(customerComboBox.getItems().get(i).toString().contentEquals(appointmentToEdit.getCustomerName())){
                System.out.println("List item value: "+ customerComboBox.getItems().get(i).toString() + " Customer name: " + appointmentToEdit.getCustomerName());
                customerComboBox.setValue(customerComboBox.getItems().get(i));
                i = customerComboBox.getItems().size();
            }else {
                i++;
            }
        }

        //Default date-time options
        endMinuteChoiceBox.getSelectionModel().selectFirst();
        endampmChoiceBox.getItems().add("AM");
        endampmChoiceBox.getItems().add("PM");
        endampmChoiceBox.getSelectionModel().selectFirst();

        //Setup start date-time options based on the appointment you're editing.
        //Populate start time choice boxes
        i = 1;
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

        //Populate end time choice boxes
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
        endampmChoiceBox.getSelectionModel().selectFirst();
        //Trim the start date/time stamp to highlight the proper hour, minute, and ampm options
        String dtStart = appointmentToEdit.getStart();

        int indexOfTimeS = dtStart.indexOf(" ");
        int indexOfMinS = dtStart.indexOf(":");

        String dS = dtStart.substring(0, indexOfTimeS);
        String hS = dtStart.substring(indexOfTimeS, indexOfMinS);
        String mS = dtStart.substring((indexOfMinS + 1), (dtStart.indexOf(":") + 3));
        hS = hS.trim();
        mS = mS.trim();
        String ampm = "AM";

        if (Integer.parseInt(hS) > 12){
            hS = (Integer.parseInt(hS) - 12) + "";
            ampm = "PM";
        } else{
            ampm = "AM";
        }
        //Setup LocalDate object to properly format date end for the datePicker.
        LocalDate daS = LocalDate.parse(dS, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        startDatePicker.setValue(daS);
        hourChoiceBox.getSelectionModel().select(hS);
        minuteChoiceBox.getSelectionModel().select(mS);
        ampmChoiceBox.getSelectionModel().select(ampm);

        //Setup end date-time options based on the appoinment you're editing.
        //Trim the end date/time stamp to highlight the proper hour, minute, and ampm options
        String dtEnd = appointmentToEdit.getEnd();

        int indexOfTimeE = dtEnd.indexOf(" ");
        int indexOfMinE = dtEnd.indexOf(":");

        String dE = dtEnd.substring(0, indexOfTimeE);
        String hE = dtEnd.substring(indexOfTimeE, indexOfMinE);
        String mE = dtEnd.substring((indexOfMinE + 1), (dtEnd.indexOf(":") + 3));
        hE = hE.trim();
        mE = mE.trim();

        if (Integer.parseInt(hE) > 12){
            hE = (Integer.parseInt(hE) - 12) + "";
            ampm = "PM";
        } else{
            ampm = "AM";
        }
        //Setup LocalDate object to properly format date end for the datePicker.
        LocalDate daE = LocalDate.parse(dE, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        endDatePicker.setValue(daE);
        endHourChoiceBox.getSelectionModel().select(hE);
        endMinuteChoiceBox.getSelectionModel().select(mE);
        endampmChoiceBox.getSelectionModel().select(ampm);
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
     * @param actionEvent Verify essential information was filled out properly, then submit it to the SQL database as an update to the passed in appointment
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
            alert.setTitle("Customer/Contact Selection Error");
            alert.setContentText("You must select a valid customer, and contact.");
            alert.show();
            return;
        }
        //Verify that a title was entered
        try{
            String title = titleTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Title entry error!");
            alert.setContentText("Please enter a valid title.");
            alert.show();
            return;
        }
        //Verify that a description was entered
        try{
            String desc = descriptionTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Description entry error!");
            alert.setContentText("Please enter a valid description.");
            alert.show();
            return;
        }
        //Verify that a location was entered
        try{
            String loc = locationTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Location entry error!");
            alert.setContentText("Please enter a valid location.");
            alert.show();
            return;
        }
        //Verify that a type was entered
        try{
            String type = typeTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Type entry error!");
            alert.setContentText("Please enter a valid type.");
            alert.show();
            return;
        }
        //Initialize variables to capture form data
        String startDateTime = "";
        String endDateTime = "";
        String timeZoneOffset = Users.currentUserTimeZone.toString();
        String startDayFormat = "-";
        String endDayFormat = "-";

        //ensure month is double value is double-digit for date/time formatter checks ahead as well as day
        if(startDatePicker.getValue().getDayOfMonth() < 10){
            startDayFormat = "-0";
        }
        if (startDatePicker.getValue().getMonthValue() < 10) {
            //Get start date/time stamp by breaking down the information populated in the ampm, minute, hour, and date selector form fields.
            if (ampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
                startDateTime = (startDatePicker.getValue().getYear() + "-0" + startDatePicker.getValue().getMonthValue() +
                        startDayFormat + startDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                        ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
                //System.out.println(startDateTime);
            } else {
                startDateTime = (startDatePicker.getValue().getYear() + "-0" + startDatePicker.getValue().getMonthValue() +
                        startDayFormat + startDatePicker.getValue().getDayOfMonth() + " " + hourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                        ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
                //System.out.println(startDateTime);
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

        //Ensure month is double value is double-digit for date/time formatter checks ahead as well as day
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
            alert.setTitle("Meeting time logic error");
            alert.setContentText("Please schedule meeting to end AFTER it starts.");
            alert.show();
            return;
        }

        //If Meeting is longer than business hours allow, fail and alert user
        if (diffInHours >= 14){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting is longer than business hours allow.");
            alert.setContentText("Please reduce meeting length to remain within a 14 hour business day.");
            alert.show();
            return;
        }

        //If meeting starts before 8am EST, or after 10pm EST, fail and alert user
        if (startConvertedValue < 8 || startConvertedValue >= 22){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting starts outside of business hours!");
            alert.setContentText("Please schedule meeting to start after 8am EST, and before 10pm EST.");
            alert.show();
            return;
        }

        //If meeting ends after 10pm EST, fail and alert user
        if (endConvertedValue < 8 || (endConvertedValue >= 22 && endConvertedValueMin > 0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting ends outside of business hours!");
            alert.setContentText("Please schedule meeting to end before 10pm EST.");
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
                alert.setTitle("Meeting conflict");
                alert.setContentText("This meeting starts during another of this client's meetings titled " + appsToCheck.get(i).getTitle() + "!");
                alert.show();
                return;
            }
            if (end.isAfter(appsToCheck.get(i).getStartStamp()) && end.isBefore(appsToCheck.get(i).getEndStamp())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Meeting conflict");
                alert.setContentText("This meeting ends during another of this client's meetings titled " + appsToCheck.get(i).getTitle() + "!");
                alert.show();
                return;
            }
            i++;
        }

        //After passing checks, run AppointmentsQuery.insert, and either load the AppointmentsViewForm, or display an error if the appointment wasn't updated successfully.
        if (AppointmentsQuery.update(titleTextField.getText(), descriptionTextField.getText(), locationTextField.getText(),
                typeTextField.getText(), customerList.get(customerComboBox.getSelectionModel().getSelectedIndex()).getID(),
                Users.currentUser.getUserName(), contactList.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                appointmentToEdit.getID(), userList.get(userComboBox.getSelectionModel().getSelectedIndex()).getId(),
                startDateTime, endDateTime, timeZoneOffset) != 0){

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsViewForm.fxml"));

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1200.0, 600.0);
            stage.setTitle("Appointments page");

            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to update appointment with the provided information!");
            alert.setContentText("Something must be wrong.");
            alert.show();
        }
    }
}

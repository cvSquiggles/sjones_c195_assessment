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
import javafx.util.converter.LocalDateStringConverter;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AddAppointmentFormController implements Initializable {

    public Label welcomeUserLabel;
    public Label timeZoneLabel;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipTextField;
    public TextField phoneTextField;
    public ComboBox countryComboBox;
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
    private ObservableList<Users> userList = FXCollections.observableArrayList();

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

        //Populate start time choice boxes
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
        endampmChoiceBox.getItems().add("AM");
        endampmChoiceBox.getItems().add("PM");
        endampmChoiceBox.getSelectionModel().selectFirst();
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

    public void onActionHomeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Home Page");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Appointments Page");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionCreateButton(ActionEvent actionEvent) throws SQLException, IOException {

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

        try{
            String title = descriptionTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Description entry error!");
            alert.setContentText("Please enter a valid description.");
            alert.show();
            return;
        }

        try{
            String title = locationTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Location entry error!");
            alert.setContentText("Please enter a valid location.");
            alert.show();
            return;
        }

        try{
            String title = typeTextField.getText();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Type entry error!");
            alert.setContentText("Please enter a valid type.");
            alert.show();
            return;
        }

        //Get form data
        int customerID = Customers.customerOptions.get(customerComboBox.getSelectionModel().getSelectedIndex()).getID();
        int contactID = Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID();
        int assignedUserID = Users.userOptions.get(userComboBox.getSelectionModel().getSelectedIndex()).getId();

        String startDateTime = "";
        String endDateTime = "";
        String timeZoneOffset = Users.currentUserTimeZone.toString();
        String currentUser = Users.currentUser.getUserName();
        String title = titleTextField.getText();
        String desc = descriptionTextField.getText();
        String loc = locationTextField.getText();
        String type = typeTextField.getText();

        //Get start date/time stamp
        if (ampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
            startDateTime = (startDatePicker.getValue().getYear() + "-" + startDatePicker.getValue().getMonthValue() +
                    "-" + startDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(hourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                    ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            //System.out.println(startDateTime);
        }
        else{
            startDateTime = (startDatePicker.getValue().getYear() + "-" + startDatePicker.getValue().getMonthValue() +
                    "-" + startDatePicker.getValue().getDayOfMonth() + " " + hourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                    ":" + minuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
            //System.out.println(startDateTime);
        }

        //Get end date/time stamp
        if (endampmChoiceBox.getSelectionModel().getSelectedItem().toString() == "PM" && Integer.valueOf(endHourChoiceBox.getSelectionModel().getSelectedItem().toString()) != 12) {
            endDateTime = (endDatePicker.getValue().getYear() + "-" + endDatePicker.getValue().getMonthValue() +
                    "-" + endDatePicker.getValue().getDayOfMonth() + " " + (Integer.valueOf(endHourChoiceBox.getSelectionModel().getSelectedItem().toString()) + 12) +
                    ":" + endMinuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
        }
        else{
            endDateTime = (endDatePicker.getValue().getYear() + "-" + endDatePicker.getValue().getMonthValue() +
                    "-" + endDatePicker.getValue().getDayOfMonth() + " " + endHourChoiceBox.getSelectionModel().getSelectedItem().toString() +
                    ":" + endMinuteChoiceBox.getSelectionModel().getSelectedItem().toString() + ":00");
        }

        //Query to verify date/times are valid
        //Referenced https://stackoverflow.com/questions/4759248/difference-between-two-dates-in-mysql and,
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

        int startConvertedValue = Integer.parseInt(startConverted.substring((startConverted.indexOf(':')-2), (startConverted.indexOf(':'))));
        int startConvertedValueMin = Integer.parseInt(startConverted.substring((startConverted.indexOf(':') + 1), (startConverted.indexOf(':') + 3)));
        int endConvertedValue = Integer.parseInt(endConverted.substring((endConverted.indexOf(':')-2), (endConverted.indexOf(':'))));
        int endConvertedValueMin = Integer.parseInt(endConverted.substring((endConverted.indexOf(':') + 1), (endConverted.indexOf(':') + 3)));
        int diffInHours = Integer.parseInt(diff.substring((diff.indexOf(':') - 2), (diff.indexOf(':'))));

        if (diffSeconds < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting time logic error");
            alert.setContentText("Please schedule meeting to end AFTER it starts.");
            alert.show();
            return;
        }

        if (diffInHours >= 14){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting is longer than business hours allow.");
            alert.setContentText("Please reduce meeting length to remain within a 14 hour business day.");
            alert.show();
            return;
        }

        if (startConvertedValue < 8 || startConvertedValue >= 22){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting starts outside of business hours!");
            alert.setContentText("Please schedule meeting to start after 8am EST, and before 10pm EST.");
            alert.show();
            return;
        }

        if (endConvertedValue < 8 || (endConvertedValue >= 22 && endConvertedValueMin > 0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Meeting ends outside of business hours!");
            alert.setContentText("Please schedule meeting to end before 10pm EST.");
            alert.show();
            return;
        }

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
            alert.setTitle("Unable to add appointment with the provided information!");
            alert.setContentText("Something must be wrong.");
            alert.show();
        }
    }
}

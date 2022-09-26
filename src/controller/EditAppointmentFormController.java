package controller;

import helper.AppointmentsQuery;
import helper.ContactsQuery;
import helper.CustomersQuery;
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
import java.sql.SQLException;
import java.time.LocalDate;
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
    private static Appointments appointmentToEdit = null;
    public static void appointmentToEdit(Appointments passAppointment) {appointmentToEdit = passAppointment;}
    private ObservableList<Customers> customerList = FXCollections.observableArrayList();
    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        titleTextField.setText(appointmentToEdit.getTitle());
        descriptionTextField.setText(appointmentToEdit.getDescription());
        locationTextField.setText(appointmentToEdit.getLocation());
        typeTextField.setText(appointmentToEdit.getType());
        int i = 0;

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

        //Setup start date-time options based on the appoinment you're editing.
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

        LocalDate daS = LocalDate.parse(dS, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        startDatePicker.setValue(daS);
        hourChoiceBox.getSelectionModel().select(hS);
        minuteChoiceBox.getSelectionModel().select(mS);
        ampmChoiceBox.getSelectionModel().select(ampm);

        //Setup end date-time options based on the appoinment you're editing.
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

        LocalDate daE = LocalDate.parse(dE, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Start: " + daS);
        System.out.println("End: " + daE);
        endDatePicker.setValue(daE);
        endHourChoiceBox.getSelectionModel().select(hE);
        endMinuteChoiceBox.getSelectionModel().select(mE);
        endampmChoiceBox.getSelectionModel().select(ampm);
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

    public void onActionCreateButton(ActionEvent actionEvent) throws SQLException {


        AppointmentsQuery.update(titleTextField.getText(), descriptionTextField.getText(), locationTextField.getText(),
                typeTextField.getText(), customerList.get(customerComboBox.getSelectionModel().getSelectedIndex()).getID(), Users.currentUser.getId(),
                contactList.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(), appointmentToEdit.getID());
    }
}

package controller;

import helper.ContactsQuery;
import helper.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;
import model.Contacts;
import model.Customers;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        titleTextField.setText(appointmentToEdit.getTitle());
        descriptionTextField.setText(appointmentToEdit.getDescription());
        locationTextField.setText(appointmentToEdit.getLocation());
        typeTextField.setText(appointmentToEdit.getType());
        int i = 0;

        while (i < contactComboBox.getItems().size()){
            if(contactComboBox.getItems().get(i).toString().contentEquals(appointmentToEdit.getContactName())){
                System.out.println("List item value: "+ contactComboBox.getItems().get(i).toString() + " Contact name: " + appointmentToEdit.getContactName());
                contactComboBox.setValue(contactComboBox.getItems().get(i));
                i = contactComboBox.getItems().size();
            }else {
                i++;
            }
        }





        endMinuteChoiceBox.getSelectionModel().selectFirst();
        endampmChoiceBox.getItems().add("AM");
        endampmChoiceBox.getItems().add("PM");
        endampmChoiceBox.getSelectionModel().selectFirst();

        String dt = "02-02-2022 13:44:00";

        int indexOfTime = dt.indexOf(" ");
        int indexOfMin = dt.indexOf(":");

        String d = dt.substring(0, indexOfTime);
        String h = dt.substring(indexOfTime, indexOfMin);
        String m = dt.substring((indexOfMin + 1), (dt.indexOf(":") + 3));
        h = h.trim();
        m = m.trim();
        String ampm = "AM";

        if (Integer.parseInt(h) > 12){
            h = (Integer.parseInt(h) - 12) + "";
            ampm = "PM";
        } else{
            ampm = "AM";
        }

        LocalDate da = LocalDate.parse(d, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        startDatePicker.setValue(da);
        hourChoiceBox.getSelectionModel().select(h);
        minuteChoiceBox.getSelectionModel().select(m);
        ampmChoiceBox.getSelectionModel().select(ampm);
    }

    public void onActionSignOutButton(ActionEvent actionEvent) {
    }

    public void onActionHomeButton(ActionEvent actionEvent) {
    }

    public void onActionCancelButton(ActionEvent actionEvent) {
    }

    public void onActionCreateButton(ActionEvent actionEvent) {
    }
}

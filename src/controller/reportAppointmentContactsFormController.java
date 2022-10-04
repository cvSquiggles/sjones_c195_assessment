package controller;

import helper.AppointmentsQuery;
import helper.ContactsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportAppointmentContactsFormController implements Initializable {
    public Label timeZoneLabel;
    public Label currentUserLabel_customersView;
    public TableColumn idColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIDColumn;
    public TableColumn userIDColumn;
    public TableView appointmentsTable;
    public int dateOffset;
    public RadioButton weekRadio;
    public ToggleGroup weekMonthToggle;
    public RadioButton monthRadio;
    public Button prevButton;
    public Button nextButton;
    public ComboBox contactComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        currentUserLabel_customersView.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        //Ensure dateOffset is reset
        dateOffset = 0;

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
            contactComboBox.getSelectionModel().select(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListWeek(dateOffset,
                    Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                    Users.currentUserTimeZone.toString());

            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

            appointmentsTable.setItems(appointmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;
        Users.homePageLoaded = true;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionHomeButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Home Page");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionWeekRadio(ActionEvent actionEvent) {
        try {
            dateOffset = 0;
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListWeek(dateOffset,
                    Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                    Users.currentUserTimeZone.toString());
            appointmentsTable.setItems(appointmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionMonthRadio(ActionEvent actionEvent) {
        try {
            dateOffset = 0;
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListMonth(dateOffset,
                    Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                    Users.currentUserTimeZone.toString());
            appointmentsTable.setItems(appointmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionPrevButton(ActionEvent actionEvent) {
        if (weekRadio.isSelected()) {
            try {
                dateOffset = dateOffset - 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListWeek(dateOffset,
                        Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                        Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                dateOffset = dateOffset - 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListMonth(dateOffset,
                        Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                        Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onActionNextButton(ActionEvent actionEvent) {
        if (weekRadio.isSelected()) {
            try {
                dateOffset = dateOffset + 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListWeek(dateOffset,
                        Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                        Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                dateOffset = dateOffset + 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListMonth(dateOffset,
                        Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                        Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onActionContactComboBox(ActionEvent actionEvent) throws SQLException {
        dateOffset = 0;
        if (weekRadio.isSelected()) {
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListWeek(dateOffset,
                    Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                    Users.currentUserTimeZone.toString());
            appointmentsTable.setItems(appointmentList);
        } else{
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectContactAppointmentsListMonth(dateOffset,
                    Contacts.contactOptions.get(contactComboBox.getSelectionModel().getSelectedIndex()).getID(),
                    Users.currentUserTimeZone.toString());
            appointmentsTable.setItems(appointmentList);
        }
    }
}

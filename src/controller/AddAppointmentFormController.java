package controller;

import helper.ContactsQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Countries;
import model.FirstLevelDivisions;
import model.Contacts;

import java.net.URL;
import java.sql.SQLException;
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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contacts> contactList = null;
        try {
            contactList = ContactsQuery.selectContacts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(contactList.get(0).getName());

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

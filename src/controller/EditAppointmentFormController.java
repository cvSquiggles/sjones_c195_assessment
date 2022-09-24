package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

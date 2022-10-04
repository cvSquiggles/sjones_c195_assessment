package controller;

import helper.AppointmentsQuery;
import helper.CustomersQuery;
import helper.JDBC;
import helper.UsersQuery;
import javafx.beans.property.Property;
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
import model.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AppointmentsViewFormController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        currentUserLabel_customersView.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        //Ensure dateOffset is reset
        dateOffset = 0;

        try {
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset, Users.currentUserTimeZone.toString());

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

    public void onActionDeleteButton(ActionEvent actionEvent) throws SQLException, IOException {
        //Display an alert requesting delete confirmation.
        if(appointmentsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete target null");
            alert.setContentText("No target selected for deletion from the list");
            alert.show();
            return;
        }

        //Get the selected appointment from the table
        Appointments selectedAppointment = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Appointment selected for deletion.");
        alert.setContentText("Are you sure you want to delete this appointment?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getText() == "Yes") {
                //Delete the appointment from the appointments table.
                try {
                    int rowsReturned = AppointmentsQuery.delete(selectedAppointment.getID());
                    if (rowsReturned != 0) {
                        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                        alert3.setTitle("Delete confirmation");
                        alert3.setContentText("Appointment ID " + selectedAppointment.getID() + " of type " + selectedAppointment.getType() + " was deleted successfully.");
                        alert3.show();
                        if(weekRadio.isSelected()) {
                            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset, Users.currentUserTimeZone.toString());
                            appointmentsTable.setItems(appointmentList);
                        }
                        else{
                            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset, Users.currentUserTimeZone.toString());
                            appointmentsTable.setItems(appointmentList);
                        }
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Unable to delete appointment with the provided information!");
                        alert2.setContentText("Something must be wrong.");
                        alert2.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                return;
            }
        });
    }


    public void onActionEditButton(ActionEvent actionEvent) throws IOException {
        if (appointmentsTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Edit Error");
            alert.setContentText("No appointment was selected from the list.");
            alert.show();
            return;
        }
        else {
            //Get the selected appointment and pass it
            Appointments selectedAppointment = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                return;
            }
            EditAppointmentFormController.appointmentToEdit(selectedAppointment);

            Parent root = FXMLLoader.load(getClass().getResource("/view/EditAppointmentViewForm.fxml"));

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1200.0, 600.0);
            stage.setTitle("Update Appointment");

            stage.setScene(scene);
            stage.show();
        }
    }

    public void onActionAddButton(ActionEvent actionEvent) throws IOException, SQLException {
        //Load the Add Customer form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Add Appointment");

        stage.setScene(scene);
        stage.show();
    }

    public void onMouseClickAppointmentsTable(MouseEvent mouseEvent) {
    }

    public void onActionWeekRadio(ActionEvent actionEvent) {
        try {
            dateOffset = 0;
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset, Users.currentUserTimeZone.toString());
            appointmentsTable.setItems(appointmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionMonthRadio(ActionEvent actionEvent) {
        try {
            dateOffset = 0;
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset, Users.currentUserTimeZone.toString());
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
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset, Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                dateOffset = dateOffset - 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset, Users.currentUserTimeZone.toString());
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
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset, Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                dateOffset = dateOffset + 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset, Users.currentUserTimeZone.toString());
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
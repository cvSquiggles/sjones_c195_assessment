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

        //Set time zone
        Locale myLocale = Locale.getDefault();
        ZoneId myTimeZoneID = TimeZone.getDefault().toZoneId();
        timeZoneLabel.setText(myTimeZoneID.toString());

        //Ensure dateOffset is reset
        dateOffset = 0;

        try {
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset);

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
        Users.currentUser = "";

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

    }


    public void onActionEditButton(ActionEvent actionEvent) {

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
        /*if(appointmentsTable.getSelectionModel().isEmpty()) {
            return;
        }

        //Get the selected customer from the table
        Customers selectedAppointment = (Customers) customersTable.getSelectionModel().getSelectedItem();

        try {
            ResultSet rs = AppointmentsQuery.selectByClient(selectedCustomer.getID());
            if(rs.next()){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("This client has appointments in the system.");
                alert2.setContentText("All appointments associated with a client must be deleted before a client can be removed from the system.");
                alert2.show();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    public void onActionWeekRadio(ActionEvent actionEvent) {
        try {
            dateOffset = 0;
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset);
            appointmentsTable.setItems(appointmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionMonthRadio(ActionEvent actionEvent) {
        try {
            dateOffset = 0;
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset);
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
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset);
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                dateOffset = dateOffset - 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset);
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
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListWeek(dateOffset);
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                dateOffset = dateOffset + 1;
                System.out.println(dateOffset);
                ObservableList<Appointments> appointmentList = AppointmentsQuery.selectAppointmentsListMonth(dateOffset);
                appointmentsTable.setItems(appointmentList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
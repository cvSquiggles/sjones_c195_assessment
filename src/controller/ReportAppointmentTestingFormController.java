package controller;

import helper.AppointmentsQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * Controls the ReportAppointmentTestingForm.fxml
 * Queries the database for appointments filtered for Type containing the string of characters 'test', or, 'Test' and displays them
 */
public class ReportAppointmentTestingFormController implements Initializable {
    public TableView appointmentsTable;
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
    public Label currentUserLabel_customersView;
    public Label timeZoneLabel;

    /**
     * Populate UI, and query database for all appointments with a type containing the string of characters 'test', or 'Test'
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        currentUserLabel_customersView.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        try {
            ObservableList<Appointments> appointmentList = AppointmentsQuery.selectTesting(Users.currentUserTimeZone.toString());

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

    /**
     *
     * @param actionEvent clear currentUser data, and set homePageLoaded to false again, then return to the login form.
     * @throws IOException
     */
    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;
        Users.homePageLoaded = false;

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
    
}

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
import model.Customers;
import model.Users;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class CustomersViewFormController implements Initializable{

    //public Label currentUser;
    //public Label currentTimeZone;
    public TableView customersTable;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn addressColumn;
    public TableColumn postalCodeColumn;
    public TableColumn phoneColumn;
    public TableColumn divisionColumn;
    public Label timeZoneLabel;
    public Label currentUserLabel_customersView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone
        Locale myLocale = Locale.getDefault();
        ZoneId myTimeZoneID = TimeZone.getDefault().toZoneId();
        timeZoneLabel.setText(myTimeZoneID.toString());

        //Set username display
        //currentUserLabel_customersView.setText("Welcome " + Users.currentUser + " | ");

        try {
            ObservableList<Customers> customerList = CustomersQuery.selectCustomerList();

            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

            customersTable.setItems(customerList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionWelcomeUserLabel(MouseEvent mouseEvent) {
    }

    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = "";

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionHomeButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = "";

        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Home Page");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionDeleteButton(ActionEvent actionEvent) {
    }

    public void onActionEditButton(ActionEvent actionEvent) {
    }

    public void onActionAddButton(ActionEvent actionEvent) throws IOException {
        //Load the Add Customer form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Add Customer");

        stage.setScene(scene);
        stage.show();
    }
}
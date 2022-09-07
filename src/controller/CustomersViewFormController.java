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
import model.Inventory;
import model.Product;
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

    public void onActionDeleteButton(ActionEvent actionEvent) throws SQLException, IOException {
        //Display an alert requesting delete confirmation.
        if(customersTable.getSelectionModel().isEmpty()) {
            return;
        }

        //Get the selected customer from the table
        Customers selectedCustomer = (Customers) customersTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer selected for deletion.");
        alert.setContentText("Are you sure you want to delete this customer?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getText() == "Yes") {
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
                }
                //Delete the customer from the customers table.
                try {
                    int rowsReturned = CustomersQuery.deleteCustomer(selectedCustomer.getID());
                    if (rowsReturned != 0) {
                        ObservableList<Customers> customerList = CustomersQuery.selectCustomerList();
                        customersTable.setItems(customerList);
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Unable to add customer with the provided information!");
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


    public void onActionEditButton(ActionEvent actionEvent) {
        if (customersTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Edit Error");
            alert.setContentText("No customer was selected from the list.");
            alert.show();
            return;
        }


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

    public void onMouseClickCustomerTable(MouseEvent mouseEvent) {
        /*if(customersTable.getSelectionModel().isEmpty()) {
            return;
        }

        //Get the selected customer from the table
        Customers selectedCustomer = (Customers) customersTable.getSelectionModel().getSelectedItem();

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
}
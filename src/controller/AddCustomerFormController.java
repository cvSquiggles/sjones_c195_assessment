package controller;

import helper.CustomersQuery;
import helper.FirstLevelDivisionsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipTextField;
    public TextField phoneTextField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;

    public ObservableList<String> countryOptionsComboList = FXCollections.observableArrayList();
    public ObservableList<String> divisionOptionsComboList = FXCollections.observableArrayList();
    public Label welcomeUserLabel;
    public Label timeZoneLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        welcomeUserLabel.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        int i = 0;
        while(i < Countries.countryOptions.size()){
            try {
                countryOptionsComboList.add(Countries.countryOptions.get(i).getName());
            }
            catch (Exception e){

                }
            i++;
        }

        countryComboBox.setItems(countryOptionsComboList);

        i = 0;
        while(i < FirstLevelDivisions.divisionOptions.size()){
            try {
                divisionOptionsComboList.add(FirstLevelDivisions.divisionOptions.get(i).getDivision());
            }
            catch (Exception e){

            }
            i++;
        }

        divisionComboBox.setItems(divisionOptionsComboList);

        /*switch (Users.currentUserLocale.getCountry()) {
            case ("US"):
                countryComboBox.
                break;
            default:
                System.out.println("Combo box didn't load!" + " | " + Users.currentUserLocale.getCountry());
        }*/

    }

    public void onActionCreateButton(ActionEvent actionEvent) throws SQLException, IOException {
        if (CustomersQuery.createCustomer(nameTextField.getText(), addressTextField.getText(), zipTextField.getText(),
                phoneTextField.getText(), Users.currentUser.getUserName(),
                divisionComboBox.getSelectionModel().getSelectedItem().toString(), Users.currentUserTimeZone.toString()) != 0){

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1200.0, 600.0);
            stage.setTitle("Customers Page");

            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to add customer with the provided information!");
            alert.setContentText("Something must be wrong.");
            alert.show();
        }
    }

    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Customers Page");

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

    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionDivisionComboBox(ActionEvent actionEvent) {

    }

    public void onActionCountryComboBox(ActionEvent actionEvent) throws SQLException {
        int i = 0;
        divisionOptionsComboList.clear();
        FirstLevelDivisions.divisionOptionsFiltered = FirstLevelDivisionsQuery.select(countryComboBox.getSelectionModel().getSelectedItem().toString());
        while(i < (FirstLevelDivisions.divisionOptionsFiltered.size())){
            try {
                divisionOptionsComboList.add(FirstLevelDivisions.divisionOptionsFiltered.get(i).getDivision());
            }
            catch (Exception e){

            }
            i++;
        }

        divisionComboBox.getSelectionModel().clearSelection();
    }

    public void onActionWelcomeUserLabel(MouseEvent mouseEvent) {
    }
}

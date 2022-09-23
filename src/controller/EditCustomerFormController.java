package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Part;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerFormController implements Initializable {
    private static Customers customerToEdit = null;
    public Label welcomeUserLabel;
    public Label timeZoneLabel;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipTextField;
    public TextField phoneTextField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button signOutButton;
    public Button homeButton;
    public Button cancelButton;
    public Button createButton;
    public ObservableList<String> countryOptionsComboList = FXCollections.observableArrayList();
    public ObservableList<String> divisionOptionsComboList = FXCollections.observableArrayList();

    public static void customerToEdit(Customers passCustomer) {customerToEdit = passCustomer;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        //Populate text fields
        nameTextField.setText(customerToEdit.getName());
        addressTextField.setText(customerToEdit.getAddress());
        zipTextField.setText(customerToEdit.getPostalCode());
        phoneTextField.setText(customerToEdit.getPhone());
        String sql = "SELECT Country";
        //countryComboBox.getSelectionModel().select(customerToEdit.get)
    }

    public void onActionCountryComboBox(ActionEvent actionEvent) {
    }

    public void onActionDivisionComboBox(ActionEvent actionEvent) {
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

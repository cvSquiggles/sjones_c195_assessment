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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerFormController implements Initializable {
    private static Customers customerToEdit = null;
    private static int[] countryDiv = null;
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
    public static void customerCountryDiv(int[] passCountryDiv) {countryDiv = passCountryDiv;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int i = 0;
        while(i < Countries.countryOptions.size()){
            try {
                countryOptionsComboList.add(Countries.countryOptions.get(i).getName());
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
            i++;
        }

        countryComboBox.setItems(countryOptionsComboList);
        countryComboBox.getSelectionModel().select(countryDiv[1]);

        i = 0;
        try {
            FirstLevelDivisions.divisionOptionsFiltered = FirstLevelDivisionsQuery.select(countryComboBox.getSelectionModel().getSelectedItem().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(i < (FirstLevelDivisions.divisionOptionsFiltered.size())){
            try {
                divisionOptionsComboList.add(FirstLevelDivisions.divisionOptionsFiltered.get(i).getDivision());
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
            i++;
        }

        divisionComboBox.setItems(divisionOptionsComboList);

        //Populate text fields
        nameTextField.setText(customerToEdit.getName());
        addressTextField.setText(customerToEdit.getAddress());
        zipTextField.setText(customerToEdit.getPostalCode());
        phoneTextField.setText(customerToEdit.getPhone());
        divisionComboBox.getSelectionModel().select(countryDiv[0]);
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

    public void onActionDivisionComboBox(ActionEvent actionEvent) {
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

    public void onActionHomeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Home Page");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Customers Page");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionCreateButton(ActionEvent actionEvent) throws SQLException, IOException {
        CustomersQuery.updateCustomer(nameTextField.getText(), addressTextField.getText(), zipTextField.getText(), phoneTextField.getText(),
                Users.currentUser.getUserName(), Users.currentUserTimeZone.toString(), divisionComboBox.getSelectionModel().getSelectedItem().toString(), customerToEdit.getID());

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Customers Page");

        stage.setScene(scene);
        stage.show();

    }
}

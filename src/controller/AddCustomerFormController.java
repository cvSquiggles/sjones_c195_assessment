package controller;

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

        /*switch (Users.currentUserLocale.getCountry()) {
            case ("US"):
                countryComboBox.
                break;
            default:
                System.out.println("Combo box didn't load!" + " | " + Users.currentUserLocale.getCountry());
        }*/

    }

    public void onActionCreateButton(ActionEvent actionEvent) {
    }

    public void onActionCancelButton(ActionEvent actionEvent) {
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

    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = "";

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
        System.out.println(countryComboBox.getSelectionModel().getSelectedItem().toString());
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
    }

    public void onActionWelcomeUserLabel(MouseEvent mouseEvent) {
    }
}

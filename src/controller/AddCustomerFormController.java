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
    public Label welcomeUserLabel;
    public Label timeZoneLabel;
    private ResourceBundle rb = Users.currentUserRB;

    /**
     * Populate the UI as well as the combo box options
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        welcomeUserLabel.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        //Iterate through the country options and add them to the countryOptionsComboList
        int i = 0;
        while(i < Countries.countryOptions.size()){
            try {
                countryOptionsComboList.add(Countries.countryOptions.get(i).getName());
            }
            catch (Exception e){

                }
            i++;
        }
        //Assign the countryOptionsComboList to the countryComboBox
        countryComboBox.setItems(countryOptionsComboList);

        //Refresh i, and iterate through the firstLevelDivisions options to populate the divisionOptionsComboList
        i = 0;
        while(i < FirstLevelDivisions.divisionOptions.size()){
            try {
                divisionOptionsComboList.add(FirstLevelDivisions.divisionOptions.get(i).getDivision());
            }
            catch (Exception e){

            }
            i++;
        }
        //Assign the divisionOptionsComboList to the divisionComboBox
        divisionComboBox.setItems(divisionOptionsComboList);
    }

    /**
     *
     * @param actionEvent Verify essential information was filled out properly,
     *                    then submit it to the SQL database as a new customer
     * @throws SQLException
     * @throws IOException
     */
    public void onActionCreateButton(ActionEvent actionEvent) throws SQLException, IOException {
        //Verify that a name was entered
       if(nameTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Name") + " " + rb.getString("entry") + " " +
                    rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " + rb.getString("a") + " " +
                    rb.getString("valid") + " " + rb.getString("name") + ".");
            alert.show();
            return;
        }
        //Verify that an address was entered
        if(addressTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Address") + " " + rb.getString("entry") + " " + rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " +
                    rb.getString("address") + ".");
            alert.show();
            return;
        }
        //Verify that a zip was entered
        if(zipTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zip " +  rb.getString("entry") + " " +  rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                            rb.getString("a") + " " + rb.getString("valid") + " zip.");
            alert.show();
            return;
        }
        if(phoneTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Phone") + " " + rb.getString("entry") + " " +
                    rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " +
                    rb.getString("phone") + ".");
            alert.show();
            return;
        }
        //Verify that a division was selected
        if(divisionComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Division") + " " + rb.getString("selection") + " " +
                    rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " + rb.getString("division") + ".");
            alert.show();
            return;
        }
        //Verify that a division was selected
        if(countryComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Country") + " " + rb.getString("selection") + " " +
                    rb.getString("error") + "!");
            alert.setContentText( rb.getString("Please") + " " + rb.getString("enter") + " " +
                    rb.getString("a") + " " + rb.getString("valid") + " " +
                    rb.getString("country") + ".");
            alert.show();
            return;
        }
        //After passing checks, run customersQuery.createCustomer, and either load the CustomersViewForm, or display an error if the customer wasn't created successfully.
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
            alert.setTitle( rb.getString("Unable") + " " + rb.getString("to") + " " +
                    rb.getString("add") + " " + rb.getString("customer") + " " + rb.getString("with") + " " +
                    rb.getString("the") + " " + rb.getString("provided") + " " +
                    rb.getString("information") + "!");
            alert.setContentText( rb.getString("Something") + " " + rb.getString("must") + " " +
                    rb.getString("be") + " " + rb.getString("wrong") + ".");
            alert.show();
        }
    }

    /**
     *
     * @param actionEvent Return to the CustomersViewForm
     * @throws IOException
     */
    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Customers Page");

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
     * @param actionEvent on selection of a country, query the database for divisions associated with that country, and repopulate the divisionComboBox options
     * @throws SQLException
     */
    public void onActionCountryComboBox(ActionEvent actionEvent) throws SQLException {
        //Clear the divisionOptionsComboList and iterate through the divisions associated with the country selected
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
}

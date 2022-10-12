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
import model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the EditCustomerForm.fxml
 * Populates the form with information passed in from the previous form on initialization
 * On submission, pulls data from the form and runs it through logic checks before attempting to submit it to the database via Customer.updateCustomer()
 */
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
    public ObservableList<String> countryOptionsComboList = FXCollections.observableArrayList(); //List to populate with country options
    public ObservableList<String> divisionOptionsComboList = FXCollections.observableArrayList(); //List to populate with division options

    public static void customerToEdit(Customers passCustomer) {customerToEdit = passCustomer;}  //Function to pass in a customer object
    public static void customerCountryDiv(int[] passCountryDiv) {countryDiv = passCountryDiv;}  //Function to pass in a selected customers' countryDivision info
    private ResourceBundle rb = Users.currentUserRB;

    /**
     * Populate UI, as well as combo box list options, and populate form based on information passed in from customerToEdit(), and customerCountryDiv()
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
                throw new RuntimeException(e);
            }
            i++;
        }
        //Assign the countryOptionsComboList to the countryComboBox, then set it to the value that was passed in
        countryComboBox.setItems(countryOptionsComboList);
        countryComboBox.getSelectionModel().select(countryDiv[1]);

        //Refresh i, and iterate through the firstLevelDivisions options to populate the divisionOptionsComboList
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
        //Assign the divisionOptionsComboList to the divisionComboBox
        divisionComboBox.setItems(divisionOptionsComboList);

        //Populate text fields, and set divisionComboBox to the value passed in
        nameTextField.setText(customerToEdit.getName());
        addressTextField.setText(customerToEdit.getAddress());
        zipTextField.setText(customerToEdit.getPostalCode());
        phoneTextField.setText(customerToEdit.getPhone());
        divisionComboBox.getSelectionModel().select(countryDiv[0]);
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
      * @param actionEvent Verify essential information was filled out properly,
     *                    then submit it to the SQL database as a customer update
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
        if(CustomersQuery.updateCustomer(nameTextField.getText(), addressTextField.getText(), zipTextField.getText(), phoneTextField.getText(),
                Users.currentUser.getUserName(), Users.currentUserTimeZone.toString(), divisionComboBox.getSelectionModel().getSelectedItem().toString(), customerToEdit.getID()) != 0) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersViewForm.fxml"));

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1200.0, 600.0);
            stage.setTitle("Customers Page");

            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to update customer with the provided information!");
            alert.setContentText("Something must be wrong.");
            alert.show();
        }
    }
}

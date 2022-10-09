package controller;

import helper.AppointmentsQuery;
import helper.CustomersQuery;
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
import java.net.URL;
import java.util.ResourceBundle;
import static controller.EditCustomerFormController.customerToEdit;


public class CustomersViewFormController implements Initializable{
    public TableView customersTable;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn addressColumn;
    public TableColumn postalCodeColumn;
    public TableColumn phoneColumn;
    public TableColumn divisionColumn;
    public Label timeZoneLabel;
    public Label currentUserLabel_customersView;
    private ResourceBundle rb = Users.currentUserRB;

    /**
     * Populate UI as well as the customer list.
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
     * @param actionEvent Verify a customer is selected from the table, then prompt the user to confirm deletion.
     * @throws SQLException
     * @throws IOException
     * Lambda is used here in the 'alert.showAndWait().ifPresent(type ->{' to pass in the clicked button
     */
    public void onActionDeleteButton(ActionEvent actionEvent) throws SQLException, IOException {
        //Display an alert requesting delete confirmation.
        if(customersTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( rb.getString("Delete") + " " + rb.getString("target") + " " + rb.getString("null") + "!");
            alert.setContentText( rb.getString("No") + " " + rb.getString("target") + " " + rb.getString("selected") + " " +
                    rb.getString("for") + " " + rb.getString("deletion") + " " + rb.getString("from") + " " +
                    rb.getString("the") + " " + rb.getString("list"));
            alert.show();
            return;
        }

        //Get the selected customer from the table
        Customers selectedCustomer = (Customers) customersTable.getSelectionModel().getSelectedItem();
        //Prompt the user for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle( rb.getString("Customer") + " " + rb.getString("selected") + " " + rb.getString("for") + " " + rb.getString("deletion") + ".");
        alert.setContentText( rb.getString("Are") + " " + rb.getString("you") + " " + rb.getString("sure") + " " +
                rb.getString("you") + " " + rb.getString("want") + " " + rb.getString("to") + " " +
                rb.getString("delete") + " " + rb.getString("this") + " " + rb.getString("customer") + "?");
        ButtonType yesButton = new ButtonType(rb.getString("Yes"), ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType(rb.getString("Cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        //Show alert and wait for user input.
        alert.showAndWait().ifPresent(type -> {
            if (type.getText() == rb.getString("Yes")) {
                try {
                    ResultSet rs = AppointmentsQuery.selectByCustomer(selectedCustomer.getID(), Users.currentUserTimeZone.toString());

                    if(rs.next()){
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle( rb.getString("This") + " " + rb.getString("client") + " " + rb.getString("has") + " " +
                                rb.getString("appointments") + " " + rb.getString("in") + " " + rb.getString("the") + " " +
                                rb.getString("system") + ".");
                        alert2.setContentText( rb.getString("All") + " " + rb.getString("appointments") + " " + rb.getString("associated") + " " +
                                rb.getString("with") + " " + rb.getString("a") + " " + rb.getString("client") + " " +
                                rb.getString("must") + " " + rb.getString("be") + " " + rb.getString("deleted") + " " +
                                rb.getString("before") + " " + rb.getString("a") + " " + rb.getString("client") + " " +
                                rb.getString("can") + " " + rb.getString("be") + " " + rb.getString("removed") + " " +
                                rb.getString("from") + " " + rb.getString("the") + " " + rb.getString("system") + ".");
                        alert2.show();
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                //Delete the customer from the customers table.
                try {
                    //Query the database for the selected customers' ID
                    int rowsReturned = CustomersQuery.deleteCustomer(selectedCustomer.getID());
                    if (rowsReturned != 0) {
                        //Display delete confirmation
                        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                        alert3.setTitle(rb.getString("Delete") + " " + rb.getString("confirmation"));
                        alert3.setContentText(rb.getString("Customer") + " " + selectedCustomer.getName() +
                                rb.getString("with") + " ID " + selectedCustomer.getID() + " " + rb.getString("was") + " " +
                                rb.getString("deleted") + " " + rb.getString("successfully") + ".");
                        alert3.show();
                        //Reload table post delete.
                        ObservableList<Customers> customerList = CustomersQuery.selectCustomerList();
                        customersTable.setItems(customerList);
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle( rb.getString("Unable") + " " + rb.getString("to") + " " + rb.getString("delete") + " " +
                                rb.getString("customer") + " " + rb.getString("with") + " " + rb.getString("the") + " " +
                                rb.getString("provided") + " " + rb.getString("information") + " " + "!");
                        alert2.setContentText( rb.getString("Something") + " " + rb.getString("must") + " " +
                                rb.getString("be") + " " + rb.getString("wrong") + " " + ".");
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

    /**
     *
     * @param actionEvent Get selected customer from the table, and pass it into the EditCustomerFormController
     *                    along with the customers' country division ID,then load the EditCustomerForm
     * @throws IOException
     * @throws SQLException
     */
    public void onActionEditButton(ActionEvent actionEvent) throws IOException, SQLException {
        //Confirm that a customer is selected.
        if (customersTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("Customer") + " " + rb.getString("Edit") + " " + rb.getString("Error"));
            alert.setContentText(rb.getString("No") + " " + rb.getString("customer") + " " + rb.getString("was") + " " +
                    rb.getString("selected") + " " + rb.getString("from") + " " + rb.getString("the") + " " + rb.getString("list") + " " + ".");
            alert.show();
            return;
        }
        else {
            //Get the selected customer and pass it
            Customers selectedCustomer = (Customers)customersTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                return;
            }
            //Pass customer to the edit form, and query for the customers country division ID to populate the field on the edit form
            customerToEdit(selectedCustomer);
            EditCustomerFormController.customerCountryDiv(CustomersQuery.selectCountryDiv(selectedCustomer.getDivisionID()));
            //Load edit form
            Parent root = FXMLLoader.load(getClass().getResource("/view/EditCustomerForm.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200.0, 600.0);
            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     *
     * @param actionEvent Load the AddCustomersViewForm
     * @throws IOException
     */
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
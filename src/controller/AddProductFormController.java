package controller;

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
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

/**
 * Controller class to handle the AddProductForm.fxml
 */
public class AddProductFormController implements Initializable {
    public TextField partSearchTextField;
    public TextField productNameTextField;
    public TextField productInvTextField;
    public TextField productPriceTextField;
    public TextField productMaxTextField;
    public TextField productMinTextField;
    public TableView unassignedPartTable;
    public TableColumn unassignedPartIDColumn;
    public TableColumn unassignedPartNameColumn;
    public TableColumn unassignedInventoryLevelColumn;
    public TableColumn unassignedPriceColumn;
    public TableView assignedPartTable;
    public TableColumn assignedPartIDColumn;
    public TableColumn assignedPartNameColumn;
    public TableColumn assignedInventoryLevelColumn;
    public TableColumn assignedPriceColumn;
    public Button addPartButton;
    public Button removeAssociatedPartButton;
    public Button saveButton;
    public Button cancelButton;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Assign values to the assigned and unassigned part tables.
        unassignedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        unassignedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unassignedInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        unassignedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        assignedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assignedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assignedInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assignedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Part> parts= Inventory.getAllParts();
        unassignedPartTable.setItems(parts);

        assignedPartTable.setItems(associatedParts);
    }
    /**
     *
     * @param actionEvent When hitting the return key in the Part Search text field, run lookupPart(String), then lookupPart(int) if that fails.
     */
    public void onAction_partSearchTextField(ActionEvent actionEvent) {
        //If the part search text field is empty, load all parts.
        if (partSearchTextField.getText().isEmpty()) {
            unassignedPartTable.setItems(Inventory.getAllParts());
            return;
        }
        //Store the search query text and perform a text search on the list of parts.
        String partQuery = partSearchTextField.getText();
        ObservableList<Part> parts = Inventory.lookupPart(partQuery);
        //If the text search fails, cycle through the list of parts and compare ID values to see if a part can be found based on ID.
        if (parts.size() == 0 ){
            try{
                Part targetPart = Inventory.lookupPart(Integer.parseInt(partQuery));
                if(targetPart != null){
                    parts.add(targetPart);
                    unassignedPartTable.getSelectionModel().select(targetPart);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        //Set the unassigned parts table to the search result.
        unassignedPartTable.setItems(parts);
    }

    /**
     *
     * @param actionEvent When add part button is clicked, find selected part in the unassigned list, and add it to the assigned list, as well as the newProduct's associated parts list
     */
    public void onAction_addPartButton(ActionEvent actionEvent) {
        //Check to see that a part is actually selected.
        if (unassignedPartTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part selection error");
            alert.setContentText("No part was selected in the unassigned parts list.");
            alert.show();
        } else {
            //Add the selected part to the assigned parts table.
            Part newAssociatedPart = (Part) unassignedPartTable.getSelectionModel().getSelectedItem();
            if (newAssociatedPart == null) {
                return;
            }
            associatedParts.add(newAssociatedPart);
        }
    }

    /**
     *
     * @param actionEvent When Remove associated button is clicked, find the selected assigned part and remove it from the list
     */
    public void onAction_removeAssociatedPartButton(ActionEvent actionEvent) {
        if (assignedPartTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part selection error");
            alert.setContentText("No part was selected in the assigned parts list.");
            alert.show();
        } else {
            Part partToRemove = (Part) assignedPartTable.getSelectionModel().getSelectedItem();
            if (partToRemove == null) {
                return;
            }
            associatedParts.remove(partToRemove);
        }
    }

    /**
     *
     * @param actionEvent When save button is clicked, verify the product information is formatted properly, and then save it to the Inventory product list
     * @throws IOException
     */
    public void onAction_saveButton(ActionEvent actionEvent) throws IOException {
        //Check that there is at least one assigned part.
        if (assignedPartTable.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No associated parts");
            alert.setContentText("A product must have at least one associated part to be saved!");
            alert.show();
            return;
        }
        //Check that the inventory count is a proper integer value.
        try {
            Integer.parseInt(productInvTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Count Error");
            alert.setContentText("Inventory count must be an integer value, and cannot be larger than 2147483645!!");
            alert.show();
            return;
        }
        //Check that the minimum inventory count is a proper integer value.
        try {
            Integer.parseInt(productMinTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Minimum inventory Count Error");
            alert.setContentText("Minimum inventory count must be an integer value, and cannot be larger than 2147483645!!");
            alert.show();
            return;
        }
        //Check that the maximum inventory count is a proper integer value.
        try {
            Integer.parseInt(productMaxTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Maximum inventory Count Error");
            alert.setContentText("Maximum inventory count must be an integer value, and cannot be larger than 2147483645!!");
            alert.show();
            return;
        }
        //Check that the inventory count is not greater than the maximum allowed.
        if (Integer.parseInt(productInvTextField.getText()) > Integer.parseInt(productMaxTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory overload");
            alert.setContentText("We cannot handle more inventory than the maximum set for this product.");
            alert.show();
            return;
        }
        //Check that the minimum inventory count is a proper integer value.
        if (Integer.parseInt(productInvTextField.getText()) < Integer.parseInt(productMinTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory disparity");
            alert.setContentText("We must always have at least the minimum inventory set for the product in stock.");
            alert.show();
            return;
        }
        //Check that the price is a proper numeric value.
        try {
            parseDouble(productPriceTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price syntax error");
            alert.setContentText("Price must be entered as a numeric value.");
            alert.show();
            return;
        }
        //Declare decimal format for price double.
        DecimalFormat dollarValue = new DecimalFormat("###.##");
        //Create new product with default values to store the new product data in.
        Product newProduct = new Product(-999, "", 0.00, 0,0,0);
        //Update newProduct with the user entered information.
        newProduct.setId(Inventory.autoIDGen);
        newProduct.setName(productNameTextField.getText());
        newProduct.setPrice(Double.parseDouble(dollarValue.format((Double.parseDouble(productPriceTextField.getText())))));
        newProduct.setStock(Integer.parseInt(productInvTextField.getText()));
        newProduct.setMin(Integer.parseInt(productMinTextField.getText()));
        newProduct.setMax(Integer.parseInt(productMaxTextField.getText()));
        //Cycle through the set of parts in the assigned parts table, and add each of them to the products assigned parts list.
        for(Part pt : associatedParts){
            newProduct.addAssociatedPart(pt);
        }
        //Add the new product to the Inventory product list.
        Inventory.addProduct(newProduct);
        //Increment the autoIDGen value for the next part/product.
        Inventory.autoIDGen++;
        //Load the Main Form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent When cancel button is clicked, return to the main screen
     * @throws IOException
     */
    public void onAction_cancelButton(ActionEvent actionEvent) throws IOException {
        //Load the Main Form window.
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }
}

package controller;

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
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

/**
 * Controller class to handle the ModifyProductForm.fxml
 */
public class ModifyProductFormController implements Initializable {
    public TextField partSearchTextField;
    public TextField autoIDTextField;
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
    private static Product productToChange = null; //Stores the product passed over from the Main Form via productToChange().
    private Product updatedProduct = new Product(-999, "", 0.00, 0,0,0); //Placeholder product to store all the new values in.
    private int productToChangeIndex; //Stores the index of the original part that is being modified in the Inventory Parts list.
    public static void productToChange(Product passProduct) {productToChange = passProduct;} //Used to pass products into the Modify Product Form controller.

    /**
     *
     * @param url
     * @param resourceBundle
     * Populates the text fields, as well as the assigned, and unassigned parts tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get index of the part that's being changed.
        productToChangeIndex = Inventory.getAllProducts().indexOf(productToChange);
        //Populate text fields with the products' data.
        autoIDTextField.setText(String.valueOf(productToChange.getId()));
        productNameTextField.setText(productToChange.getName());
        productInvTextField.setText(String.valueOf(productToChange.getStock()));
        productPriceTextField.setText(String.valueOf(productToChange.getPrice()));
        productMaxTextField.setText(String.valueOf(productToChange.getMax()));
        productMinTextField.setText(String.valueOf(productToChange.getMin()));
        //Set the placeholder product to all of the original products values.
        updatedProduct.setId(productToChange.getId());
        updatedProduct.setName(productToChange.getName());
        updatedProduct.setPrice(productToChange.getPrice());
        updatedProduct.setStock(productToChange.getStock());
        updatedProduct.setMin(productToChange.getMin());
        updatedProduct.setMax(productToChange.getMax());

        //Populate the unassigned and assigned parts tables.
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
        //For each part in the original products assigned parts list, add it to the new placeholder parts assigned part list as well.
        for (Part pt : productToChange.getAllAssociatedParts()){
            updatedProduct.addAssociatedPart(pt);
        }

        assignedPartTable.setItems(updatedProduct.getAllAssociatedParts());
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
     * @param actionEvent When add part button is clicked, find selected part in the unassigned list, and add it to the assigned list
     */
    public void onAction_addPartButton(ActionEvent actionEvent) {
        //Confirm that a part is selected in the unassigned parts list.
        if (unassignedPartTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part selection error");
            alert.setContentText("No part was selected in the unassigned parts list.");
            alert.show();
        } else {
            //Get the selected part from the unassigned parts list, and add it to the placeholder product's assigned part list.
            Part newAssociatedPart = (Part) unassignedPartTable.getSelectionModel().getSelectedItem();
            if (newAssociatedPart == null) {
                return;
            }
            updatedProduct.addAssociatedPart(newAssociatedPart);
        }
    }
    /**
     *
     * @param actionEvent When Remove associated button is clicked, find the selected assigned part and remove it from the list
     */
    public void onAction_removeAssociatedPartButton(ActionEvent actionEvent) {
        //Confirm that a part is selected in the assigned parts table.
        if (assignedPartTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part selection error");
            alert.setContentText("No part was selected in the assigned parts list.");
            alert.show();
        } else {
            //Get the selected part in the assigned part table, and remove it from the placeholder products assigned parts list.
            Part partToRemove = (Part) assignedPartTable.getSelectionModel().getSelectedItem();
            if (partToRemove == null) {
                return;
            }
            updatedProduct.deleteAssociatedParts(partToRemove);
        }
    }

    /**
     *
     * @param actionEvent When the save buttons is clicked, verify text field syntax, and part assignment,
     * then update the product at the original products index with the placeholder product in the Inventory Product list.
     * @throws IOException
     */
    public void onAction_saveButton(ActionEvent actionEvent) throws IOException {
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
        //Update placeholder product with new values.
        updatedProduct.setName(productNameTextField.getText());
        updatedProduct.setPrice(Double.parseDouble(dollarValue.format((Double.parseDouble(productPriceTextField.getText())))));
        updatedProduct.setStock(Integer.parseInt(productInvTextField.getText()));
        updatedProduct.setMin(Integer.parseInt(productMinTextField.getText()));
        updatedProduct.setMax(Integer.parseInt(productMaxTextField.getText()));
        //Replace part in the Inventory Product list at the original products index with the new updated product.
        Inventory.updateProduct(productToChangeIndex, updatedProduct);
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
        //Load the Main Form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }
}

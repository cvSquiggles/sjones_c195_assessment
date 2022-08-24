package controller;

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
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import static java.lang.Double.parseDouble;

/**
 * Controller class to handle the ModifyPartForm.fxml
 */
public class ModifyPartFormController implements Initializable{
    public RadioButton inHouse;
    public ToggleGroup inOut;
    public RadioButton outHouse;
    public TextField partNameTextField;
    public TextField invCountTextField;
    public TextField priceTextField;
    public TextField maxInvTextField;
    public Label macIDorCompanyLabel;
    public TextField machineIDTextField;
    public TextField minInvTextField;
    public Button savePartButton;
    public Button cancelPartButton;
    public TextField autoIDTextField;
    private static Part partToChange = null; //Stores the part passed over from the Main Form via partToChange().
    private static int partToChangeIndex; //Stores the index of the original part that is being modified in the Inventory Parts list.

    /**
     *
     * @param passPart Part passed into the Modify Part Form.
     */
    public static void partToChange(Part passPart) {partToChange = passPart;}

    /**
     *
     * @param url
     * @param resourceBundle
     * Populates all the form text fields with the partToChange data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get index of the part that's being changed.
        partToChangeIndex = Inventory.getAllParts().indexOf(partToChange);
        //Populate text fields with the parts' data.
        autoIDTextField.setText(String.valueOf(partToChange.getId()));
        partNameTextField.setText(partToChange.getName());
        invCountTextField.setText(String.valueOf(partToChange.getStock()));
        priceTextField.setText(String.valueOf(partToChange.getPrice()));
        maxInvTextField.setText(String.valueOf(partToChange.getMax()));
        minInvTextField.setText(String.valueOf(partToChange.getMin()));
        //Determine if the part is InHouse, or Outsourced, and adjust the form accordingly.
        if (partToChange instanceof InHouse){
            macIDorCompanyLabel.setText("Machine ID");
            machineIDTextField.setText(String.valueOf(((InHouse) partToChange).getMachineID()));
            inHouse.setSelected(true);
        }
        else{
            macIDorCompanyLabel.setText("Company");
            machineIDTextField.setText(((Outsourced) partToChange).getCompanyName());
            outHouse.setSelected(true);
        }

    }

    /**
     *
     * @param actionEvent When the inHouse radio button is selected, update the form accordingly.
     */
    public void onAction_inHouse(ActionEvent actionEvent) {
        if(inHouse.isSelected()){
            macIDorCompanyLabel.setText("Machine ID");
            machineIDTextField.setPromptText("Machine ID here...");
        }
        else{
            macIDorCompanyLabel.setText("Company");
            machineIDTextField.setPromptText("Company name here...");
        }
    }
    /**
     *
     * @param actionEvent When the Outsourced radio button is selected, update the form accordingly.
     */
    public void onAction_outHouse(ActionEvent actionEvent) {
        if(outHouse.isSelected()){
            macIDorCompanyLabel.setText("Company");
            machineIDTextField.setPromptText("Company name here...");
        }
        else{
            macIDorCompanyLabel.setText("Machine ID");
            machineIDTextField.setPromptText("Machine ID here...");
        }
    }

    /**
     *
     * @param actionEvent When the save button is clicked, verify data syntax, then add the updated part to the parts list at the original parts index.
     * @throws IOException
     */
    public void onAction_savePartButton(ActionEvent actionEvent) throws IOException {
        //Check that the inventory count is a proper integer.
        try {
            Integer.parseInt(invCountTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Count Error");
            alert.setContentText("Inventory count must be an integer value, and cannot be larger than 2147483645!");
            alert.show();
            return;
        }
        //Check that the minimum inventory count is a proper integer.
        try {
            Integer.parseInt(minInvTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Minimum inventory Count Error");
            alert.setContentText("Minimum inventory count must be an integer value, and cannot be larger than 2147483645!");
            alert.show();
            return;
        }
        //Check that the max inventory count is a proper integer.
        try {
            Integer.parseInt(maxInvTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Maximum inventory Count Error");
            alert.setContentText("Maximum inventory count must be an integer value, and cannot be larger than 2147483645!");
            alert.show();
            return;
        }
        //Check that the minimum inventory limit is not greater than the maximum inventory limit.
        if (Integer.parseInt(invCountTextField.getText()) > Integer.parseInt(maxInvTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory overload");
            alert.setContentText("We cannot handle more inventory than the maximum set for this part.");
            alert.show();
            return;
        }
        //Check that the inventory count entered is not greater than the minimum allowed.
        if (Integer.parseInt(invCountTextField.getText()) < Integer.parseInt(minInvTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory disparity");
            alert.setContentText("We must always have at least the minimum inventory set for the part in stock.");
            alert.show();
            return;
        }
        //Check that the price is a proper numeric value.
        try {
            parseDouble(priceTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price syntax error");
            alert.setContentText("Price must be entered as a numeric value.");
            alert.show();
            return;
        }
        //Check that the price entered isn't negative. *I don't believe this can happen, but it's here just in case.
        if (Double.parseDouble(priceTextField.getText()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price Syntax Error");
            alert.setContentText("Price cannot be a negative number!");
            alert.show();
            return;
        }
        //Declare decimal format for price double.
        DecimalFormat dollarValue = new DecimalFormat("###.##");
        //If outsourced, created Outsourced part, otherwise create InHouse part, then replace the original part in the Inventory parts list with the new part.
        if (macIDorCompanyLabel.getText().contains("Company")){
            Outsourced newPart = new Outsourced(Integer.parseInt(autoIDTextField.getText()), partNameTextField.getText(), Double.parseDouble(dollarValue.format(Double.parseDouble(priceTextField.getText()))), Integer.parseInt(invCountTextField.getText()),
                    Integer.parseInt(minInvTextField.getText()), Integer.parseInt(maxInvTextField.getText()), machineIDTextField.getText());
            Inventory.updatePart(partToChangeIndex, newPart);
        }
        else{
            InHouse newPart = new InHouse(Integer.parseInt(autoIDTextField.getText()), partNameTextField.getText(), Double.parseDouble(dollarValue.format(Double.parseDouble(priceTextField.getText()))), Integer.parseInt(invCountTextField.getText()),
                    Integer.parseInt(minInvTextField.getText()), Integer.parseInt(maxInvTextField.getText()), Integer.parseInt(machineIDTextField.getText()));
            Inventory.updatePart(partToChangeIndex, newPart);
        }
        //Blank out the variables just in case they're ever used/referenced again before being reset.
        partToChange = null;
        partToChangeIndex = -999;
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
     * @param actionEvent When the cancel button is clicked, reset the values to be safe, and load the Main Form.
     * @throws IOException
     */
    public void onAction_cancelPartButton(ActionEvent actionEvent) throws IOException {
        //Blank out the variables just in case they're ever used/referenced again before being reset.
        partToChange = null;
        partToChangeIndex = -999;
        //Load the Main Form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }
}

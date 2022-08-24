package controller;

import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static controller.ModifyPartFormController.partToChange;
import static controller.ModifyProductFormController.*;

/**
 * Controller class to handle the MainForm.fxml
 */
public class MainFormController implements Initializable {
    public Button partModifyButton;
    public Button partDeleteButton;
    public Button addPartButton;
    public Button addProductButton;
    public Button productModifyButton;
    public Button productDeleteButton;
    public Button exitButton;
    public TableView partTable;
    public TableView productTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInvLevelColumn;
    public TableColumn partPriceColumn;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInvLevelColumn;
    public TableColumn productPriceColumn;
    public TextField partSearchTextField;
    public TextField productSearchTextField;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Assign values to the parts, and products tables.
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Product> products= Inventory.getAllProducts();
        productTable.setItems(products);

        ObservableList<Part> parts= Inventory.getAllParts();
        partTable.setItems(parts);
    }

    /**
     *
     * @param actionEvent When the modify button is clicked in the part table, will open the modify part window loading the part highlighted in the parts table.
     * @throws IOException
     */
    public void onAction_partModifyButton(ActionEvent actionEvent) throws IOException {
        //Check to confirm that there is a part selected.
        if (partTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("No part was selected in the part list.");
            alert.show();
        }
        else {
            //Get the selected part and pass it to the ModifyPartForm with partToChange().
            Part selectedPart = (Part)partTable.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }

            partToChange(selectedPart);
            System.out.println(selectedPart.getName());
            //Load the Modify Part Form.
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200.0, 800.0);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     *
      * @param actionEvent When the Delete button is clicked on the part table, it deletes the selected part if it is not a subcomponent of an existing product.
     */
    public void onAction_partDeleteButton(ActionEvent actionEvent) {
        //Check to confirm that there is a part selected.
        if (partTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("No part was selected in the part list.");
            alert.show();
            return;
        }
        //Get the selected part, and then confirm that it isn't a subcomponent of an existing product.
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
        for (Product pd : Inventory.getAllProducts()){
            for (Part pt : pd.getAllAssociatedParts()){
                if (pt == selectedPart){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cannot delete");
                    alert.setContentText("This part is a subcomponent of existing products, so it cannot be deleted.");
                    alert.show();
                    return;
                }
            }
        }
        //Display an alert requesting delete confirmation.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Part selected for deletion.");
        alert.setContentText("Are you sure you want to delete this part?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if(type.getText() == "Yes"){
                //Delete the part.
                Inventory.deletePart((Part) partTable.getSelectionModel().getSelectedItem());
            }
            else {
                return;
            }
        });
    }

    /**
     * RUNTIME ERROR
     * @param actionEvent When the modify product button is clicked.
     * @throws IOException
     * Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader$MethodHandler.invoke(FXMLLoader.java:1857)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader$ControllerMethodEventHandler.handle(FXMLLoader.java:1724)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
     * 	at javafx.base@18.0.1/javafx.event.Event.fireEvent(Event.java:198)
     * 	at javafx.graphics@18.0.1/javafx.scene.Node.fireEvent(Node.java:8797)
     * 	at javafx.controls@18.0.1/javafx.scene.control.Button.fire(Button.java:203)
     * 	at javafx.controls@18.0.1/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:208)
     * 	at javafx.controls@18.0.1/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
     * 	at javafx.base@18.0.1/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
     * 	at javafx.base@18.0.1/javafx.event.Event.fireEvent(Event.java:198)
     * 	at javafx.graphics@18.0.1/javafx.scene.Scene$MouseHandler.process(Scene.java:3881)
     * 	at javafx.graphics@18.0.1/javafx.scene.Scene.processMouseEvent(Scene.java:1874)
     * 	at javafx.graphics@18.0.1/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2607)
     * 	at javafx.graphics@18.0.1/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
     * 	at javafx.graphics@18.0.1/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
     * 	at java.base/java.security.AccessController.doPrivileged(Native Method)
     * 	at javafx.graphics@18.0.1/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
     * 	at javafx.graphics@18.0.1/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
     * 	at javafx.graphics@18.0.1/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
     * 	at javafx.graphics@18.0.1/com.sun.glass.ui.View.handleMouseEvent(View.java:551)
     * 	at javafx.graphics@18.0.1/com.sun.glass.ui.View.notifyMouse(View.java:937)
     * 	at javafx.graphics@18.0.1/com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
     * 	at javafx.graphics@18.0.1/com.sun.glass.ui.win.WinApplication.lambda$runLoop$3(WinApplication.java:184)
     * 	at java.base/java.lang.Thread.run(Thread.java:834)
     * Caused by: java.lang.reflect.InvocationTargetException
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
     * 	at com.sun.javafx.reflect.Trampoline.invoke(MethodUtil.java:77)
     * 	at jdk.internal.reflect.GeneratedMethodAccessor2.invoke(Unknown Source)
     * 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
     * 	at javafx.base@18.0.1/com.sun.javafx.reflect.MethodUtil.invoke(MethodUtil.java:275)
     * 	at javafx.fxml@18.0.1/com.sun.javafx.fxml.MethodHelper.invoke(MethodHelper.java:84)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader$MethodHandler.invoke(FXMLLoader.java:1852)
     * 	... 46 more
     * Caused by: java.lang.NullPointerException: Location is required.
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3324)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3287)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3255)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3227)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3203)
     * 	at javafx.fxml@18.0.1/javafx.fxml.FXMLLoader.load(FXMLLoader.java:3196)
     * 	at controller.MainFormController.onAction_productModifyButton(MainFormController.java:130)
     * 	... 57 more
     *
     * 	I corrected this by refactoring the ModifyProductForm.fxml file name from the typo -ProductFrom
     */
    public void onAction_productModifyButton(ActionEvent actionEvent) throws IOException {
        //Confirm that a product is selected.
        if (productTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("No product was selected in the product list.");
            alert.show();
        }
        else {
            //Get the selected product, and pass it to the Modify Product From with productToChange().
            Product selectedProduct = (Product)productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                return;
            }

            productToChange(selectedProduct);
            //Load the Modify Product Form.
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200.0, 800.0);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     *
     * @param actionEvent When the Delete button is clicked on the product table, it deletes the selected product.
     */
    public void onAction_productDeleteButton(ActionEvent actionEvent) {
        //Check to confirm that there is a product selected.
        if (productTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Delete Error");
            alert.setContentText("No product was selected in the product list.");
            alert.show();
        }

        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        //Check that there are no parts associated with the product selected.
        if (selectedProduct.getAllAssociatedParts().size() == 0) {

            //Display an alert requesting delete confirmation.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Product selected for deletion.");
            alert.setContentText("Are you sure you want to delete this product?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.getText() == "Yes") {
                    //Delete the product from the Inventory products list.
                    Inventory.deleteProduct((Product) productTable.getSelectionModel().getSelectedItem());
                } else {
                    return;
                }
            });
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Delete Error");
            alert.setContentText("This product has component parts associated with it. Please un-assign all parts before deleting.");
            alert.show();
            return;
        }
    }

    /**
     *
     * @param actionEvent When the exit button is clicked the program closes out.
     */
    public void onAction_exitButton(ActionEvent actionEvent) {
        //Exit the application
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     *
     * @param actionEvent When the add button is clicked on the parts table, we're taken to the AddPartForm.fxml window.
     * @throws IOException
     */
    public void onAction_addPartButton(ActionEvent actionEvent) throws IOException {
        //Load the Add Part Form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 800.0);
        stage.setTitle("Add Part");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent When the add button is clicked on the products table, we're taken to the AddProductForm.fxml window.
     * @throws IOException
     */
    public void onAction_addProductButton(ActionEvent actionEvent) throws IOException {
        //Load the Add Product Form.
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 729.0);
        stage.setTitle("Add Product");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent When hitting the return key in the Part Search text field, run lookupPart(String), then lookupPart(int) if that fails.
     */
    public void onAction_partSearchTextField(ActionEvent actionEvent) {
        //If the part search field is empty, load the entire parts list.
        if (partSearchTextField.getText().isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
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
                    partTable.getSelectionModel().select(targetPart);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        //Set the part table to the search result.
        partTable.setItems(parts);
    }

    /**
     *
     * @param actionEvent When hitting the return key in the Product Search text field, run lookupProduct(String), then lookupProduct(int) if that fails.
     */
    public void onAction_productSearchTextField(ActionEvent actionEvent) {
        //If the product search field is empty, load the entire products list.
        if (productSearchTextField.getText().isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());
            return;
        }
        //Store the search query text and perform a text search on the list of products.
        String productQuery = productSearchTextField.getText();
        ObservableList<Product> products = Inventory.lookupProduct(productQuery);
        //If the text search fails, cycle through the list of products and compare ID values to see if a product can be found based on ID.
        if (products.size() == 0) {
            try{
                Product targetProduct = Inventory.lookupProduct(Integer.parseInt(productQuery));
                if(targetProduct != null){
                    products.add(targetProduct);
                    productTable.getSelectionModel().select(targetProduct);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        //Set the product table to the search result.
        productTable.setItems(products);
    }
}

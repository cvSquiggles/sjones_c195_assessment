package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.text.DecimalFormat;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static java.lang.Double.parseDouble;

/**
 * Controller class to handle the AddPartForm.fxml
 */
public class AddPartFormController implements Initializable{
    public RadioButton inHouse;
    public ToggleGroup inOut;
    public RadioButton outHouse;
    public TextField partNameTextField;
    public TextField invCountTextField;
    public TextField priceTextField;
    public TextField maxInvTextField;
    public TextField machineIDTextField;
    public TextField minInvTextField;
    public Button savePartButton;
    public Button cancelPartButton;
    public Label macIDorCompanyLabel;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * RUNTIME ERROR
     * @param actionEvent When the Save Part button is clicked, save new part is either InHouse, or Outsourced accordingly
     * @throws IOException
     *
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
     * Caused by: java.lang.NumberFormatException: For input string: "40.234823"
     * 	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
     * 	at java.base/java.lang.Integer.parseInt(Integer.java:652)
     * 	at java.base/java.lang.Integer.parseInt(Integer.java:770)
     * 	at controller.AddPartFormController.onAction_savePartButton(AddPartFormController.java:126)
     * 	... 57 more
     *
     * 	I corrected this by updating the Integer.parseInteger to Double.parseDouble for the price check on value being below 0.
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
            alert.setTitle("Maximum inventory count error");
            alert.setContentText("Maximum inventory count must be an integer value, and cannot be larger than 2147483645!");
            alert.show();
            return;
        }
        //Check that the minimum inventory limit is not greater than the maximum inventory limit.
        if (Integer.parseInt(minInvTextField.getText()) > Integer.parseInt(maxInvTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory limit syntax error");
            alert.setContentText("The minimum inventory limit cannot be greater than the maximum inventory limit.");
            alert.show();
            return;
        }
        //Check that the inventory count entered is not greater than the maximum allowed.
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
        //Check that the inventory amount entered isn't negative. *I don't believe this can happen, but this is here just in case.
        if (Integer.parseInt(invCountTextField.getText()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Syntax Error");
            alert.setContentText("Inventory count cannot be a negative number!");
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
        //If outsourced, created Outsourced part, otherwise create InHouse part.
        if (macIDorCompanyLabel.getText().contains("Company")){
            Outsourced newPart = new Outsourced(Inventory.autoIDGen, partNameTextField.getText(), Double.parseDouble(dollarValue.format(Double.parseDouble(priceTextField.getText()))), Integer.parseInt(invCountTextField.getText()),
                    Integer.parseInt(minInvTextField.getText()), Integer.parseInt(maxInvTextField.getText()), machineIDTextField.getText()){

            };
            Inventory.allParts.add(newPart);
        }
        else{
            //Confirm that the machine ID is a proper integer value.
            try {
                Integer.parseInt(machineIDTextField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Machine ID Syntax Error");
                alert.setContentText("Machine ID must be an integer value, and cannot be larger than 2147483645!");
                alert.show();
                return;
            }
            //Confirm that the machineID isn't negative. *I don't believe this can be done, but it's here just in case.
            if (Integer.parseInt(machineIDTextField.getText()) < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Machine ID Syntax Error");
                alert.setContentText("Machine ID cannot be a negative number!");
                alert.show();
                return;
            }


            InHouse newPart = new InHouse(Inventory.autoIDGen, partNameTextField.getText(), Double.parseDouble(dollarValue.format(Double.parseDouble(priceTextField.getText()))), Integer.parseInt(invCountTextField.getText()),
                    Integer.parseInt(minInvTextField.getText()), Integer.parseInt(maxInvTextField.getText()), Integer.parseInt(machineIDTextField.getText())){

            };
            //Add the newly created part to the Inventory part last.
            Inventory.allParts.add(newPart);
        }
        //Increment the autoIDGen value for the next part/product.
        Inventory.autoIDGen++;
        //Load up the MainForm again.
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent When the cancel button is clicked, returns to MainFormController
     * @throws IOException
     */
    public void onAction_cancelPartButton(ActionEvent actionEvent) throws IOException {
        //Load the MainForm again.
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent When the inHouse radio button is selected, update machineID label to Company
     */
    public void onAction_inHouse(ActionEvent actionEvent) {
        //Update the machineID/CompanyName text field and labels according to the radio button selection.
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
     * @param actionEvent When the Outsourced radio button is selected, update machineID label to machine ID
     */
    public void onAction_outHouse(ActionEvent actionEvent) {
        //Update the machineID/CompanyName text field and labels according to the radio button selection.
        if(outHouse.isSelected()){
            macIDorCompanyLabel.setText("Company");
            machineIDTextField.setPromptText("Company name here...");
        }
        else{
            macIDorCompanyLabel.setText("Machine ID");
            machineIDTextField.setPromptText("Machine ID here...");
        }
    }
}

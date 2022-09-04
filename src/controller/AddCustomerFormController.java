package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipTextField;
    public TextField phoneTextField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Users.currentUserZoneID.ge

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

    public void onActionCountryComboBox(ActionEvent actionEvent) {
    }

    public void onActionWelcomeUserLabel(MouseEvent mouseEvent) {
    }
}

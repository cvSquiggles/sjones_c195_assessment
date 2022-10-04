package controller;

import helper.AppointmentsQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportAppointmentTotalsFormController implements Initializable {
    public Label currentUserLabel_customersView;
    public Label timeZoneLabel;
    public Button homeButton;
    public Button signOutButton;
    public TableView reportTable;
    private ObservableList<ObservableList> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set time zone label
        timeZoneLabel.setText(Users.currentUserZoneID.toString());

        //Set username display
        currentUserLabel_customersView.setText("Current User: " + Users.currentUser.getUserName() + " | ");

        ResultSet rs = null;
        try {
            rs = AppointmentsQuery.selectTotals();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        int rsColCount = 0;
        try {
            rsColCount = rs.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        //References code from https://stackoverflow.com/questions/18941093/how-to-fill-up-a-tableview-with-database-data LAMBDA USED HERE!!!!
        for(int i=0 ; i < rsColCount; i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = null;
            try {
                col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                    new SimpleStringProperty(param.getValue().get(j).toString()));

            reportTable.getColumns().addAll(col);
        }

        /********************************
         * Data added to ObservableList *
         ********************************/
        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rsColCount; i++){
                //Iterate Column
                try {
                    row.add(rs.getString(i));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            data.add(row);

        }

        //FINALLY ADDED TO TableView
        reportTable.setItems(data);
    }

    public void onMouseClickAppointmentsTable(MouseEvent mouseEvent) {
    }

    public void onActionSignOutButton(ActionEvent actionEvent) throws IOException {
        Users.currentUser = null;
        Users.homePageLoaded = true;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInForm.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Scheduling Manager v0.1");

        stage.setScene(scene);
        stage.show();
    }

    public void onActionHomeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomePageForm.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1200.0, 600.0);
        stage.setTitle("Home Page");

        stage.setScene(scene);
        stage.show();
    }

}

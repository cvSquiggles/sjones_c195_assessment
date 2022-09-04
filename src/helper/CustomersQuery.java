package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersQuery {

    public static ObservableList<Customers> selectCustomerList() throws SQLException {
        String sql = "SELECT cs.*, d.Division FROM customers as cs JOIN first_level_divisions as d ON cs.Division_ID = d.Division_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        while(rs.next()){
            Customers customerValue = new Customers(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),
                    rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                    rs.getString("Created_By"), rs.getInt("Division_ID") ,rs.getString("Division"));

            customerList.add(customerValue);
        }

        return customerList;
    }

}

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

    public static int createCustomer(String name, String address, String zip, String phone, String user, String division) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, division);

        ResultSet rs = ps.executeQuery();
        rs.next();

        String sql2 = "INSERT INTO client_schedule.customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?, ?, ?, ?, current_timestamp(), ?, current_timestamp(), ?, ?)";
        PreparedStatement ps2 = JDBC.getConnection().prepareStatement(sql2);

        ps2.setString(1, name);
        ps2.setString(2, address);
        ps2.setString(3, zip);
        ps2.setString(4, phone);
        ps2.setString(5, user);
        ps2.setString(6, user);
        ps2.setInt(7, rs.getInt("Division_ID"));

        int rowsReturned = ps2.executeUpdate();

        return rowsReturned;
    }

    public static int deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, id);

        int rowsReturned = ps.executeUpdate();
        return rowsReturned;
    }

}

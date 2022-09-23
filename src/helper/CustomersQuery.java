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

    public static int[]selectCountryDiv(int divID) throws SQLException{
        int[] countryDiv = new int[]{0,0};
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        int countryID = 0;

        while(rs.next()){
            if(divID == rs.getInt("Division_ID")){
                countryID = rs.getInt("Country_ID");
            }
        }

        String sql3 = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps3 = JDBC.getConnection().prepareStatement(sql3);

        ps3.setInt(1, countryID);
        ResultSet rs3 = ps3.executeQuery();

        int i0 = 0;

        while(rs3.next()){
            if(divID == rs3.getInt("Division_ID")){
                countryDiv[0] = i0;
            }
            else {
                i0++;
            }
        }

        String sql2 = "SELECT * FROM countries";
        PreparedStatement ps2 = JDBC.getConnection().prepareStatement(sql2);

        ResultSet rs2 = ps2.executeQuery();

        int i1 = 0;

        while(rs2.next()){
            if(countryID == rs2.getInt("Country_ID")){
                countryDiv[1] = i1;
            }
            else {
                i1++;
            }
        }

        return countryDiv;
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

    public static int updateCustomer(String name, String address, String zip, String phone, String currentUser, String timeZoneOffset, String division, int customerID) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, division);

        ResultSet rs = ps.executeQuery();
        rs.next();

        //UPDATE `client_schedule`.`customers` SET `Address` = '49 Horse Manor ', `Postal_Code` = '28199' WHERE (`Customer_ID` = '3');
        String sql2 = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?,  Postal_Code = ?, Phone = ?, " +
                "Last_Update = CONVERT_TZ(CURRENT_TIMESTAMP, ?, '+00:00'), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps2 = JDBC.getConnection().prepareStatement(sql2);

        ps2.setString(1, name);
        ps2.setString(2, address);
        ps2.setString(3, zip);
        ps2.setString(4, phone);
        ps2.setString(5, timeZoneOffset);
        ps2.setString(6, currentUser);
        ps2.setInt(7, rs.getInt("Division_ID"));
        ps2.setInt(8, customerID);

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

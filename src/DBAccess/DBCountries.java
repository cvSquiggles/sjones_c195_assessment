package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.*;

public class DBCountries {

    public static ObservableList<Countries> getAllCountries(){

        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try {

            String sql = "SELECT * FROM Countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryID, countryName);

                clist.add(C);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }
}

package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains various methods of querying the database for country information
 */
public class CountriesQuery {
    /**
     *
     * @return an ObservableList of all the countries that exist in the database
     * @throws SQLException
     */
    public static ObservableList<Countries> select() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        while (rs.next()) {
            Countries countryData = new Countries(rs.getInt("Country_ID"), rs.getString("Country"));

            countryList.add(countryData);
        }

        return countryList;
    }


}

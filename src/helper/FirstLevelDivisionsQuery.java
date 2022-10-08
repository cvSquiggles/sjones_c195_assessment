package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionsQuery {
    /**
     *
     * @return an ObservableList of the first-level divisions stored in the database
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> select() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();

        while (rs.next()) {
            FirstLevelDivisions divisionsData = new FirstLevelDivisions(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));

            divisionsList.add(divisionsData);
        }

        return divisionsList;
    }

    /**
     *
     * @param countryName the name of the country to search for associated first-level divisions
     * @return an ObservableList of first-level divisions associated with the provided country name
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> select(String countryName) throws SQLException {
        String sql = "SELECT Country_ID FROM countries WHERE Country = ?";
        PreparedStatement ps1 = JDBC.getConnection().prepareStatement(sql);

        ps1.setString(1, countryName);

        ResultSet rs1 = ps1.executeQuery();

        sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps2 = JDBC.getConnection().prepareStatement(sql);

        rs1.next();
        ps2.setInt(1, rs1.getInt("Country_ID"));

        ResultSet rs2 = ps2.executeQuery();

        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();

        while (rs2.next()) {
            FirstLevelDivisions divisionsData = new FirstLevelDivisions(rs2.getInt("Division_ID"), rs2.getString("Division"), rs2.getInt("Country_ID"));

            divisionsList.add(divisionsData);
        }

        return divisionsList;
    }
}

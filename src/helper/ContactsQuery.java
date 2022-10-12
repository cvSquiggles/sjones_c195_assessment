package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains various methods of querying the database for contact information
 */
public class ContactsQuery {
    /**
     *
     * @return an ObservableList of the contacts that exist in the database
     * @throws SQLException
     */
    public static ObservableList<Contacts> selectContacts() throws SQLException {
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Contacts contactValue = new Contacts(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
            contactList.add(contactValue);
        }

        return contactList;
    }
}

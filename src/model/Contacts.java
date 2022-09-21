package model;

import helper.JDBC;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contacts {
    private int id;
    private String name;

    private String email;
    public static ObservableList<Contacts> contactOptions;

    public Contacts(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String searchByID(int id) throws SQLException {
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        rs.next();
        String contactName = rs.getString("Contact_Name");

        return contactName;
    }

    public int getID() {return id;}

    public String getName() {return name;}

    public String getEmail() {return email;}
}

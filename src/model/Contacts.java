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

    /**
     *
     * @param id
     * @param name
     * @param email
     */
    public Contacts(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @return the ID of the contact
     */
    public int getID() {return id;}

    /**
     *
     * @return the name of the contact
     */
    public String getName() {return name;}

}

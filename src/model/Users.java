package model;

import javafx.collections.ObservableList;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.ResourceBundle;

public class Users {
    private int id;
    private String userName;
    private String password;
    private String createdBy;
    public static Users currentUser; //The current user signed in
    public static ZoneId currentUserZoneID; //The current users' ZoneID
    public static Locale currentUserLocale; //The current users' Locale
    public static ZoneOffset currentUserTimeZone; //The current users' time zone offset
    public static ResourceBundle currentUserRB; //The current users' ResourceBundle
    public static ObservableList<Users> userOptions; //Stores the list of users
    public static boolean homePageLoaded; //Boolean used to track whether it's a users first time loading the home page

    /**
     *
     * @param id
     * @param userName
     * @param password
     * @param createdBy
     */
    public Users(int id, String userName, String password, String createdBy){
        this.id = id;
        this. userName = userName;
        this.password = password;
        this.createdBy = createdBy;
    }

    /**
     *
     * @return the ID of the user
     */
    public int getId() {return id;}

    /**
     *
     * @return the name of the user
     */
    public String getUserName() {return userName;}

    /**
     *
     * @return the password of the user
     */
    public String getPassword() {return password;}

    /**
     *
     * @return whether the user has loaded the homepage
     */
    public boolean getHomePageLoaded() {return homePageLoaded;}

}

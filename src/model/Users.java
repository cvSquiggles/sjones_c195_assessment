package model;

import javafx.collections.ObservableList;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Users {
    private int id;
    private String userName;
    private String password;
    private String createdBy;
    public static Users currentUser;
    public static ZoneId currentUserZoneID;
    public static Locale currentUserLocale;
    public static ZoneOffset currentUserTimeZone;
    public static ResourceBundle currentUserRB;
    public static ObservableList<Users> userOptions;
    public static boolean homePageLoaded;

    public Users(int id, String userName, String password, String createdBy){
        this.id = id;
        this. userName = userName;
        this.password = password;
        this.createdBy = createdBy;
    }

    public int getId() {return id;}

    public String getUserName() {return userName;}

    public String getPassword() {return password;}
    public boolean getHomePageLoaded() {return homePageLoaded;}

    public String getCreatedBy() {return createdBy;}
}

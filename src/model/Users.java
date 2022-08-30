package model;

public class Users {
    private int id;
    private String userName;
    private String password;
    private String createdBy;
    public static String currentUser;

    public Users(int id, String userName, String password, String createdBy){
        this.id = id;
        this. userName = userName;
        this.password = password;
        this.createdBy = createdBy;
    }

    public int getId() {return id;}

    public String getUserName() {return userName;}

    public String getPassword() {return password;}

    public String getCreatedBy() {return createdBy;}
}

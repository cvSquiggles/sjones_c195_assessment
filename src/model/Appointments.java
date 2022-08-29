package model;

public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String createdBy;
    private String customerID;
    private String userID;
    private String contactID;

    public Appointments(int id, String title, String description, String location, String type){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createdBy = createdBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getID() {return id;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getLocation() {return location;}

    public String getType() {return type;}

    public String getCreatedBy() {return createdBy;}

    public String getCustomerID() {return customerID;}

    public String getUserID() {return userID;}

    public String getContactID() {return contactID;}
}

package model;

public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String createdBy;
    private String start;
    private String end;
    private String customerName;
    private int customerID;
    private int userID;
    private String contactName;
    private int contactID;

    public Appointments(int id, String title, String description, String location, String contactName, String type, String createdBy,
                        String start, String end ,int customerID, int userID, int contactID, String customerName){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.createdBy = createdBy;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.customerName = customerName;
    }

    public int getID() {return id;}
    public String getCustomerName() {return customerName;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getLocation() {return location;}

    public String getType() {return type;}

    public String getCreatedBy() {return createdBy;}

    public String getStart() {return start;}

    public String getEnd() {return end;}

    public String getContactName() {return contactName;}

    public int getCustomerID() {return customerID;}

    public int getUserID() {return userID;}

    public int getContactID() {return contactID;}
}

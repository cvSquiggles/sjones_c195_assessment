package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String createdBy;
    private String start;
    private LocalDateTime startStamp;
    private String end;
    private LocalDateTime endStamp;
    private String customerName;
    private int customerID;
    private int userID;
    private String contactName;
    private int contactID;

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param location
     * @param contactName
     * @param type
     * @param createdBy
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @param customerName
     */
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startStamp = LocalDateTime.parse(start, formatter);
        endStamp = LocalDateTime.parse(end, formatter);
    }

    /**
     *
     * @return the appointment ID
     */
    public int getID() {return id;}

    /**
     *
     * @return the customer name associated with the appointment
     */
    public String getCustomerName() {return customerName;}

    /**
     *
     * @return the title of the associated appointment
     */
    public String getTitle() {return title;}

    /**
     *
     * @return the description of the associated appointment
     */
    public String getDescription() {return description;}

    /**
     *
     * @return the location of the associated appointment
     */
    public String getLocation() {return location;}

    /**
     *
     * @return the type of the associated appointment
     */
    public String getType() {return type;}

    /**
     *
     * @return the start date/time of the associated appointment as a string
     */
    public String getStart() {return start;}

    /**
     *
     * @return the start date/time of the associated appointment as a LocalDateTime
     */
    public LocalDateTime getStartStamp() {return startStamp;}

    /**
     *
     * @return the end date/time of the associated appointment as a string
     */
    public String getEnd() {return end;}

    /**
     *
     * @return the end date/time of the associated appointment as a LocalDateTime
     */
    public LocalDateTime getEndStamp() {return endStamp;}

    /**
     *
     * @return the name of the contact tied to the associated appointment
     */
    public String getContactName() {return contactName;}

    /**
     *
     * @return the ID of the user tied to the associated appointment
     */
    public int getUserID() {return userID;}

}

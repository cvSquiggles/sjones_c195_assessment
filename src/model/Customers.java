package model;

import javafx.collections.ObservableList;

public class Customers {
    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String createdBy;
    private int divisionID;
    private String division;
    public static ObservableList<Customers> customerOptions; //Stores list of customers

    /**
     *
     * @param ID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param createdBy
     * @param divisionID
     * @param division
     */
    public Customers(int ID, String name, String address, String postalCode, String phone, String createdBy, int divisionID, String division){
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.divisionID = divisionID;
        this.division = division;
    }

    /**
     *
     * @return the customers' ID
     */
    public int getID() {return ID;}

    /**
     *
     * @return the customers' name
     */
    public String getName() {return name;}

    /**
     *
     * @return the customers' address
     */
    public String getAddress() {return address;}

    /**
     *
     * @return the customers' zip
     */
    public String getPostalCode() {return postalCode;}

    /**
     *
     * @return the customers' phone number
     */
    public String getPhone() {return phone;}

    /**
     *
     * @return the customers' division ID
     */
    public int getDivisionID() {return divisionID;}
}
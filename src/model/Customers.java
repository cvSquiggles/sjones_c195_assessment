package model;

import javafx.collections.FXCollections;
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
    public static ObservableList<Customers> customerOptions;

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

    public int getID() {return ID;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public String getPostalCode() {return postalCode;}

    public String getPhone() {return phone;}

    public int getDivisionID() {return divisionID;}

    public String getCreatedBy() {return createdBy;}

    public String getDivision() {return division;}
}

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String createdBy;
    private int divisionID;
    private String division;

    public Customers(int id, String name, String address, String postalCode, String phone, int divisionID, String division){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.division = division;
    }

    public int getID() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public String getPostalCode() {return postalCode;}

    public String getPhone() {return phone;}

    public int getDivisionID() {return divisionID;}

    public String getCreatedBy() {return createdBy;}

    public String getDivision() {return division;}
}

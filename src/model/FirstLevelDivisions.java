package model;

import helper.FirstLevelDivisionsQuery;
import javafx.collections.ObservableList;

public class FirstLevelDivisions {

    private int id;
    private String division;
    private int countryID;
    public static ObservableList<FirstLevelDivisions> divisionOptions;
    public static ObservableList<FirstLevelDivisions> divisionOptionsFiltered;

    public FirstLevelDivisions(int id, String division, int countryID){
        this.id = id;
        this.division = division;
        this.countryID = countryID;
    }

    public int getId() {return id;}

    public String getDivision() {return division;}

    public int getCountryID() {return countryID;}
}

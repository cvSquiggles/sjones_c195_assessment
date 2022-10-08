package model;

import javafx.collections.ObservableList;

public class FirstLevelDivisions {

    private int id;
    private String division;
    private int countryID;
    public static ObservableList<FirstLevelDivisions> divisionOptions; //Stores list of division options
    public static ObservableList<FirstLevelDivisions> divisionOptionsFiltered; //Stores list of filtered division options

    /**
     *
     * @param id
     * @param division
     * @param countryID
     */
    public FirstLevelDivisions(int id, String division, int countryID){
        this.id = id;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     *
     * @return the ID of the first-level division
     */
    public int getId() {return id;}

    /**
     *
     * @return the name of the division
     */
    public String getDivision() {return division;}

}

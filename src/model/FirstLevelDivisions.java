package model;

public class FirstLevelDivisions {

    private int id;
    private String division;
    private String createdBy;
    private int countryID;

    public FirstLevelDivisions(int id, String division, String createdBy, int countryID){
        this.id = id;
        this.division = division;
        this.createdBy = createdBy;
        this.countryID = countryID;
    }

    public int getId() {return id;}

    public String getDivision() {return division;}

    public String getCreatedBy() {return createdBy;}

    public int getCountryID() {return countryID;}
}

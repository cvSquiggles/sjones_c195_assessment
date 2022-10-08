package model;

import javafx.collections.ObservableList;

public class Countries {
    private int id;
    private String name;
    public static ObservableList<Countries> countryOptions; //Stores list of countries

    /**
     *
     * @param id
     * @param name
     */
    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return the ID of the country
     */
    public int getID() {return id;}

    /**
     *
     * @return the name of the country
     */
    public String getName() {return name;}
}

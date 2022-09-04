package model;

import javafx.collections.ObservableList;

public class Countries {
    private int id;
    private String name;
    public static ObservableList<Countries> countryOptions;

    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getID() {return id;}

    public String getName() {return name;}
}

package model;

public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;

    public Appointments(int id, String title, String description, String location, String type){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    public int getID() {return id;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getLocation() {return location;}

    public String getType() {return type;}
}

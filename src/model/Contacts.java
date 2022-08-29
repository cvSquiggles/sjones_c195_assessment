package model;

public class Contacts {
    private int id;
    private String name;

    private String email;

    public Contacts(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getID() {return id;}

    public String getName() {return name;}

    public String getEmail() {return email;}
}
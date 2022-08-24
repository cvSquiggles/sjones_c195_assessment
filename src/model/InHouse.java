package model;

public class InHouse extends Part {

    private int machineID; //ID unique to InHouse parts.

    /**
     *
     * @param id The unique ID for the part, auto generated with the autoGenID integer declared in Main.
     * @param name The part name.
     * @param price The price of the part.
     * @param stock The count of how many of this part are currently in inventory.
     * @param min The minimum acceptable inventory count.
     * @param max The maximum acceptable inventory count.
     * @param machineID ID unique to InHouse parts, manually set by the user.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @return the parts machineID.
     */
    public int getMachineID(){ return machineID;}

    /**
     *
     * @param newMachineID the integer value to set the parts machineID to.
     */
    public void setMachineID(int newMachineID){ machineID = newMachineID;}
}

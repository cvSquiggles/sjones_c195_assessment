package model;

public class Outsourced extends Part{
    private String companyName;
    /**
     *
     * @param id The unique ID for the part, auto generated with the autoGenID integer declared in Main.
     * @param name The part name.
     * @param price The price of the part.
     * @param stock The count of how many of this part are currently in inventory.
     * @param min The minimum acceptable inventory count.
     * @param max The maximum acceptable inventory count.
     * @param companyName The company name that the part is outsourced from.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param newCompanyName string value to set the parts' companyName value to.
     */
    public void setCompanyName(String newCompanyName) { companyName = newCompanyName; }
}

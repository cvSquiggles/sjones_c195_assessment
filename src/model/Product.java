package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Steven J.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList(); //List of parts assigned to the product as component parts.

    /**
     *
     * @param id the unique id of the product, automatically generated using the Inventory autoIDGen integer.
     * @param name the name of the product.
     * @param price the price of the product.
     * @param stock the count of how many of this product are currently in inventory.
     * @param min the minimum acceptable inventory count.
     * @param max the maximum acceptable inventory count.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part add to the list of associatedParts
     */
    public void addAssociatedPart(Part part){ associatedParts.add(part);}

    /**
     * @param selectedAssociatedPart to delete from the list of associatedParts
     * @return whether the part was successfully removed from the list
     */
    public boolean deleteAssociatedParts(Part selectedAssociatedPart){ return associatedParts.remove(selectedAssociatedPart);}

    /**
     *
     * @return The complete list of currently associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){ return associatedParts;}

}

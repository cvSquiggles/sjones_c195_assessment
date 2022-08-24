package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Maintains a global Product and Part list, and provides functionality to modify those lists.
 */
public class Inventory {
    public static ObservableList<Product> allProducts= FXCollections.observableArrayList(); //Global list to store all products in.
    public static ObservableList<Part> allParts= FXCollections.observableArrayList(); //Global list to store all parts in.
    public static int autoIDGen; //ID used to automatically assign unique IDs to each part/product. (Set to 2 on application start)

    /**
     * run init() to initialize some parts at program start.
     */
    static {
        init();
    }

    /**
     * generates a few default parts.
     */
    public static void init() {
        if (Inventory.allParts.size() == 0) {
            InHouse newPart = new InHouse(0, "Wheel", 500.00, 20, 4, 40, 12){

            };
            Inventory.allParts.add(newPart);
            Outsourced newPart2 = new Outsourced(1, "Rim", 150.00, 20, 4, 40, "Honda"){

            };
            Inventory.allParts.add(newPart2);
        }
    }

    /**
     *
     * @param part the new part to add to the list
     */
    public static void addPart(Part part) { allParts.add(part); }

    /**
     *
     * @param product the new product to add to the list
     */
    public static void addProduct(Product product) { allProducts.add(product); }

    /**
     *
     * @param id the id to search for in the parts list
     * @return the part matching the id provided
     */
    public static Part lookupPart(int id){
        //blank out target part.
        Part targetPart = null;
        //For each part in the Inventory Parts list, look for the id passed in, if it's found, set it equal to target part.
        for (Part pt : allParts){
            if (id == pt.getId()){
                targetPart = pt;
            }
        }
        return targetPart;
    }
    /**
     *
     * @param id the id to search for in the products list
     * @return the product matching the id provided
     */
    public static Product lookupProduct(int id){
        //blank out target product.
        Product targetProduct = null;
        //For each product in the Inventory Products list, look for the id passed in, if it's found, set it equal to target product.
        for (Product pd : allProducts){
            if (id == pd.getId()){
                targetProduct = pd;
            }
        }
        return targetProduct;
    }
    /**
     *
     * @param partName the string of characters to search the part list's name values for
     * @return the list of parts with names containing the string of characters provided
     */
    public static ObservableList<Part> lookupPart(String partName){
        //Declare a new list of Parts to store the search results.
        ObservableList<Part> targetParts = FXCollections.observableArrayList();
        //Get the list of all parts in the Inventory Parts list.
        ObservableList<Part> allParts = Inventory.getAllParts();
        //For all parts in the list, if the part name contains the partName query string, add it to the list of target parts.
        for(Part pt : allParts) {
            if (pt.getName().contains(partName)){
                targetParts.add(pt);
            }
        }
        return targetParts;
    }
    /**
     *
     * @param productName the string of characters to search the product list's name values for
     * @return the list of products with names containing the string of characters provided
     */
    public static ObservableList<Product> lookupProduct(String productName){
        //Declare a new list of Products to store the search results.
        ObservableList<Product> targetProducts = FXCollections.observableArrayList();
        //Get the list of all products in the Inventory Parts list.
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        //For all products in the list, if the product name contains the productName query string, add it to the list of target products.
        for(Product pd : allProducts) {
            if (pd.getName().contains(productName)){
                targetProducts.add(pd);
            }
        }
        return targetProducts;
    }
    /**
     *
     * @param index the index value of the part we're wanting to update
     * @param selectedPart the new part that's been updated, which we place over the index of the original part
     */
    public static void updatePart(int index, Part selectedPart){ allParts.set(index, selectedPart); }
    /**
     *
     * @param index the index value of the product we're wanting to update
     * @param selectedProduct the new product that's been updated, which we place over the index of the original part
     */
    public static void updateProduct(int index, Product selectedProduct){ allProducts.set(index, selectedProduct); }
    /**
     *
     * @param selectedPart the part we've selected to delete
     * @return whether we successfully removed the part from the list
     */
    public static boolean deletePart(Part selectedPart){ return allParts.remove(selectedPart); }
    /**
     *
     * @param selectedProduct the product we've selected to delete
     * @return whether we successfully removed the product from the list
     */
    public static boolean deleteProduct(Product selectedProduct){ return allProducts.remove(selectedProduct); }
    /**
     * @return entire allProducts list
     */
    public static ObservableList<Product> getAllProducts(){ return allProducts;}
    /**
     * @return entire allParts list
     */
    public static ObservableList<Part> getAllParts(){ return allParts;}
}
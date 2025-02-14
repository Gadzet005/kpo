package project.interfaces;

/**
 * This interface provides methods to access inventory information.
 */
public interface IInventory {
    /**
     * Retrieves the unique identifier of the inventory.
     *
     * @return the unique identifier of the inventory
     */
    int getId();

    /**
     * Retrieves the name of the inventory.
     *
     * @return the name of the inventory
     */
    String getName();

    /**
     * Retrieves the description of the inventory.
     *
     * @return the description of the inventory
     */
    String getDescription();
}

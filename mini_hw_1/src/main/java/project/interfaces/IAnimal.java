package project.interfaces;

/**
 * This interface represents an entity that is alive and has certain
 * characteristics. It extends the {@link IInventory} interface, which provides
 * methods for managing inventory.
 */
public interface IAnimal extends IInventory {

    /**
     * Returns the amount of food required by this entity per day.
     *
     * @return the amount of food required per day
     */
    double getFoodPerDay();

    /**
     * Checks if this entity is in a healthy state.
     *
     * @return {@code true} if the entity is healthy, {@code false} otherwise
     */
    boolean isHealthy();

    /**
     * Checks if this entity is kind or not.
     *
     * @return {@code true} if the entity is kind, {@code false} otherwise
     */
    boolean isKind();
}

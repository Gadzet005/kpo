package project.cli.registrators;

/**
 * Interface for registering inventory in zoo system
 */
public interface IInventoryRegistrator {
    /**
     * Registers inventory items using a command-line interface.
     */
    boolean registerByCLI();
}

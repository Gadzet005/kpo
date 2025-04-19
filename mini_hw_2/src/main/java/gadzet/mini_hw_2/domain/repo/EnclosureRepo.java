package gadzet.mini_hw_2.domain.repo;

import java.util.List;

import gadzet.mini_hw_2.domain.models.Enclosure;
import gadzet.mini_hw_2.domain.valueobjects.Species;

/**
 * Repository interface for managing enclosures in a zoo or animal sanctuary.
 * Provides methods for creating, updating, retrieving, and deleting enclosures,
 * as well as for getting statistics about enclosures.
 */
public interface EnclosureRepo {
    record CreateEnclosureData(Species.Type type, int size, int maxAnimals) {
    }

    /**
     * Creates a new enclosure based on the provided data.
     *
     * @param enclosure The data needed to create a new enclosure
     * @return The created Enclosure object
     * @throws IllegalArgumentException if the enclosure data is invalid
     */
    Enclosure createEnclosure(CreateEnclosureData enclosure)
            throws IllegalArgumentException;

    /**
     * Updates an existing enclosure in the repository.
     * 
     * @param enclosure The enclosure object to be updated
     * @return true if the enclosure was successfully updated, false otherwise
     */
    boolean updateEnclosure(Enclosure enclosure);

    /**
     * Deletes an enclosure with the specified ID from the repository.
     *
     * @param id the unique identifier of the enclosure to delete
     * @return true if the enclosure was successfully deleted, false if the
     *         enclosure with the given ID was not found
     */
    boolean deleteEnclosureById(int id);

    /**
     * Retrieves an Enclosure entity by its ID.
     *
     * @param id The unique identifier of the Enclosure to retrieve
     * @return The Enclosure with the specified ID, or null if no such Enclosure
     *         exists
     */
    Enclosure getEnclosureById(int id);

    /**
     * Retrieves a list of all enclosures in the system.
     *
     * @return a list containing all enclosures currently registered in the
     *         repository
     */
    List<Enclosure> getAllEnclosures();

    /**
     * Returns the total number of enclosures in the repository.
     *
     * @return the total count of enclosures
     */
    int getTotalEnclosures();
}

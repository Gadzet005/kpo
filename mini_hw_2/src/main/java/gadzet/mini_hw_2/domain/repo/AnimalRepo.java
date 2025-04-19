package gadzet.mini_hw_2.domain.repo;

import java.time.LocalDate;
import java.util.List;

import gadzet.mini_hw_2.domain.models.Animal;
import gadzet.mini_hw_2.domain.models.Animal.Gender;
import gadzet.mini_hw_2.domain.models.Animal.HealthStatus;
import gadzet.mini_hw_2.domain.valueobjects.Species;

/**
 * Repository interface for managing Animal entities in the system. Provides
 * CRUD operations for animal data management.
 */
public interface AnimalRepo {
    public record CreateAnimalData(String name, Species species,
            LocalDate birthDate, Gender gender, String favoriteFood,
            HealthStatus status, int enclosureId) {
    }

    /**
     * Creates a new Animal entity based on the provided creation data.
     *
     * @param data The data required to create a new animal
     * @return The newly created Animal entity
     * @throws IllegalArgumentException if the provided data is invalid
     */
    Animal createAnimal(CreateAnimalData data) throws IllegalArgumentException;

    /**
     * Updates the existing animal in the repository.
     * 
     * @param animal the animal object containing updated information
     * @return true if the animal was successfully updated, false if the animal
     *         with the specified ID was not found
     */
    boolean updateAnimal(Animal animal);

    /**
     * Deletes an animal from the repository based on its ID.
     * 
     * @param id The unique identifier of the animal to be deleted
     * @return true if the animal was successfully deleted, false if the animal
     *         with the specified ID was not found
     */
    boolean deleteAnimalById(int id);

    /**
     * Retrieves an animal from the repository by its ID.
     *
     * @param id the unique identifier of the animal to retrieve
     * @return the Animal object with the specified ID
     */
    Animal getAnimalById(int id);

    /**
     * Retrieves all animals from the repository.
     *
     * @return a list containing all animals in the repository
     */
    List<Animal> getAllAnimals();

    /**
     * Returns the total number of animals in the repository.
     * 
     * @return the total count of animals
     */
    int getTotalAnimals();
}

package project.services;

import project.factories.IInventoryFactory;
import project.interfaces.IAnimal;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalStorage {
    private @Autowired AnimalClinic clinic;
    private @Autowired InventoryIdCounter idCounter;
    private ArrayList<IAnimal> animals = new ArrayList<>();

    /**
     * Adds a new animal to the storage.
     *
     * @param factory The factory that creates the new animal.
     * @return True if the animal was successfully added, false otherwise.
     */
    public <TParams> boolean addAnimal(
            IInventoryFactory<TParams, IAnimal> factory, TParams params) {
        var animal = factory.create(idCounter.getNextId(), params);

        if (!clinic.checkHealth(animal)) {
            return false;
        }

        animals.add(animal);
        return true;
    }

    /**
     * Retrieves all animals stored in the storage.
     *
     * @return An array of all animals in the storage.
     */
    public IAnimal[] getAnimals() {
        return animals.toArray(IAnimal[]::new);
    }

    /**
     * Retrieves all kind animals stored in the storage.
     *
     * @return An array of all kind animals in the storage.
     */
    public IAnimal[] getKindAnimals() {
        return animals.stream().filter(animal -> animal.isKind())
                .toArray(IAnimal[]::new);
    }

    /**
     * Calculates the total food consumption (kg) per day for all animals in the
     * storage.
     *
     * @return The total food consumption per day for all animals in the
     *         storage.
     */
    public double getFoodPerDay() {
        return animals.stream().mapToDouble(IAnimal::getFoodPerDay).sum();
    }
}

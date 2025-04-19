package gadzet.mini_hw_2.app.services.impl;

import java.util.List;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.errors.ServiceNotFoundError;
import gadzet.mini_hw_2.app.events.AnimalMoveEvent;
import gadzet.mini_hw_2.app.services.interfaces.AnimalService;
import gadzet.mini_hw_2.domain.models.Animal;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private static final ServiceNotFoundError NOT_FOUND_ERROR = new ServiceNotFoundError(
            "Animal not found");

    private final EnclosureRepo enclosureRepo;
    private final AnimalRepo animalRepo;

    @Override
    public Animal createAnimal(AnimalRepo.CreateAnimalData data)
            throws ServiceError {
        var enclosure = enclosureRepo.getEnclosureById(data.enclosureId());
        if (enclosure == null) {
            throw new ServiceNotFoundError("Enclosure not found");
        }
        if (enclosure.isFull()) {
            throw new ServiceError("Enclosure is full");
        }
        if (!data.species().isCompatible(enclosure.getType())) {
            throw new ServiceError(
                    "Animal species is not compatible with enclosure type");
        }

        try {
            var animal = animalRepo.createAnimal(data);
            enclosure.addAnimal(animal.getId());
            enclosureRepo.updateEnclosure(enclosure);
            return animal;
        } catch (IllegalArgumentException e) {
            throw new ServiceError("Invalid animal data: " + e.getMessage());
        }
    }

    @Override
    public Animal getAnimalById(int id) throws ServiceNotFoundError {
        var animal = animalRepo.getAnimalById(id);
        if (animal == null) {
            throw NOT_FOUND_ERROR;
        }
        return animal;
    }

    @Override
    public void deleteAnimalById(int id) throws ServiceNotFoundError {
        var animal = animalRepo.getAnimalById(id);
        if (animal == null) {
            throw NOT_FOUND_ERROR;
        }
        var enclosure = enclosureRepo.getEnclosureById(animal.getEnclosureId());
        if (enclosure == null) {
            throw new ServiceNotFoundError("Enclosure not found");
        }
        enclosure.removeAnimal(id);
        enclosureRepo.updateEnclosure(enclosure);

        animalRepo.deleteAnimalById(id);
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepo.getAllAnimals();
    }

    @Override
    public void moveAnimal(AnimalMoveEvent event) throws ServiceError {
        var to = enclosureRepo.getEnclosureById(event.enclosureIdTo());
        if (to == null) {
            throw new ServiceNotFoundError("Target enclosure not found");
        }
        if (to.isFull()) {
            throw new ServiceError("Target enclosure is full");
        }

        var animal = animalRepo.getAnimalById(event.animalId());
        if (animal == null) {
            throw new ServiceNotFoundError("Animal not found.");
        }
        if (!animal.getSpecies().isCompatible(to.getType())) {
            throw new ServiceError(
                    "Animal species is not compatible with enclosure type");
        }

        var from = enclosureRepo.getEnclosureById(animal.getEnclosureId());
        if (from == null) {
            throw new ServiceNotFoundError("Source enclosure not found");
        }
        if (from.getId() == to.getId()) {
            throw new ServiceError("Animal is already in the target enclosure");
        }

        from.removeAnimal(animal.getId());
        to.addAnimal(animal.getId());
        animal.setEnclosureId(to.getId());

        enclosureRepo.updateEnclosure(from);
        enclosureRepo.updateEnclosure(to);
        animalRepo.updateAnimal(animal);
    }
}
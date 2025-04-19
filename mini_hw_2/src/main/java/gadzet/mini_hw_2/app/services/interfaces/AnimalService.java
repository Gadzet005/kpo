package gadzet.mini_hw_2.app.services.interfaces;

import java.util.List;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.app.events.AnimalMoveEvent;
import gadzet.mini_hw_2.domain.models.Animal;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;

public interface AnimalService {
    Animal createAnimal(AnimalRepo.CreateAnimalData data) throws ServiceError;

    Animal getAnimalById(int id) throws ServiceError;

    void deleteAnimalById(int id) throws ServiceError;

    List<Animal> getAllAnimals();

    void moveAnimal(AnimalMoveEvent event) throws ServiceError;
}
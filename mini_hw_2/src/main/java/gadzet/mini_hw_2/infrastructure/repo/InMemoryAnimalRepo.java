package gadzet.mini_hw_2.infrastructure.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gadzet.mini_hw_2.domain.models.Animal;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;

public class InMemoryAnimalRepo implements AnimalRepo {
    private final Map<Integer, Animal> animalStorage = new HashMap<>();
    private int nextId = 0;

    @Override
    public Animal createAnimal(CreateAnimalData data) {
        Animal animal = new Animal(nextId++, data.name(), data.species(),
                data.birthDate(), data.gender(), data.favoriteFood(),
                data.status(), data.enclosureId());
        animalStorage.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public boolean updateAnimal(Animal animal) {
        if (!animalStorage.containsKey(animal.getId())) {
            return false;
        }
        animalStorage.put(animal.getId(), animal);
        return true;
    }

    @Override
    public boolean deleteAnimalById(int id) {
        return animalStorage.remove(id) != null;
    }

    @Override
    public Animal getAnimalById(int id) {
        return animalStorage.get(id);
    }

    @Override
    public List<Animal> getAllAnimals() {
        return List.copyOf(animalStorage.values());
    }

    @Override
    public int getTotalAnimals() {
        return animalStorage.size();
    }
}

package gadzet.mini_hw_2.domain.models;

import java.util.ArrayList;
import java.util.List;

import gadzet.mini_hw_2.domain.valueobjects.Species;
import lombok.Getter;

public class Enclosure {
    @Getter
    private final int id;
    @Getter
    private final Species.Type type;
    @Getter
    private final int size;
    @Getter
    private final int maxAnimals;
    @Getter
    private final List<Integer> animalIds = new ArrayList<>();

    public Enclosure(int id, Species.Type type, int size, int maxAnimals) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.maxAnimals = maxAnimals;
    }

    public boolean addAnimal(int animalId) {
        if (isFull()) {
            return false;
        }
        animalIds.add(animalId);
        return true;
    }

    public boolean hasAnimal(int animalId) {
        return animalIds.contains(animalId);
    }

    public boolean removeAnimal(int animalId) {
        return animalIds.remove(Integer.valueOf(animalId));
    }

    public boolean isFull() {
        return animalIds.size() >= maxAnimals;
    }

    public void clean() {
        // Cleaning logic here...
    }
}

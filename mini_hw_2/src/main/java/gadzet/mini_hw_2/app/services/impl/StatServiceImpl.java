package gadzet.mini_hw_2.app.services.impl;

import gadzet.mini_hw_2.app.services.interfaces.StatService;
import gadzet.mini_hw_2.domain.repo.AnimalRepo;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;

public class StatServiceImpl implements StatService {
    private final AnimalRepo animalRepo;
    private final EnclosureRepo enclosureRepo;

    public StatServiceImpl(AnimalRepo animalRepo, EnclosureRepo enclosureRepo) {
        this.animalRepo = animalRepo;
        this.enclosureRepo = enclosureRepo;
    }

    @Override
    public int getTotalAnimals() {
        return animalRepo.getAllAnimals().size();
    }

    @Override
    public int getTotalEnclosures() {
        return enclosureRepo.getAllEnclosures().size();
    }

    @Override
    public int getAvailableEnclosures() {
        return (int) enclosureRepo.getAllEnclosures().stream()
                .filter(enclosure -> !enclosure.isFull()).count();
    }
}

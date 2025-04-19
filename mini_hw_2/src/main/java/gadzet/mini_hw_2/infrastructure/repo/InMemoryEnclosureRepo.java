package gadzet.mini_hw_2.infrastructure.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gadzet.mini_hw_2.domain.models.Enclosure;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;

public class InMemoryEnclosureRepo implements EnclosureRepo {
    private final Map<Integer, Enclosure> enclosureStorage = new HashMap<>();
    private int nextId = 0;

    @Override
    public Enclosure createEnclosure(CreateEnclosureData data) {
        var enclosure = new Enclosure(nextId++, data.type(), data.size(),
                data.maxAnimals());
        enclosureStorage.put(enclosure.getId(), enclosure);
        return enclosure;
    }

    @Override
    public boolean updateEnclosure(Enclosure enclosure) {
        if (!enclosureStorage.containsKey(enclosure.getId())) {
            return false;
        }
        enclosureStorage.put(enclosure.getId(), enclosure);
        return true;
    }

    @Override
    public boolean deleteEnclosureById(int id) {
        return enclosureStorage.remove(id) != null;
    }

    @Override
    public Enclosure getEnclosureById(int id) {
        return enclosureStorage.get(id);
    }

    @Override
    public List<Enclosure> getAllEnclosures() {
        return List.copyOf(enclosureStorage.values());
    }

    @Override
    public int getTotalEnclosures() {
        return enclosureStorage.size();
    }
}

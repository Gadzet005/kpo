package gadzet.mini_hw_2.app.services.impl;

import java.util.List;

import gadzet.mini_hw_2.app.errors.ServiceNotFoundError;
import gadzet.mini_hw_2.app.services.interfaces.EnclosureService;
import gadzet.mini_hw_2.domain.models.Enclosure;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;

public class EnclosureServiceImpl implements EnclosureService {
    private static final ServiceNotFoundError NOT_FOUND_ERROR = new ServiceNotFoundError(
            "Enclosure service not found");
    private final EnclosureRepo enclosureRepo;

    public EnclosureServiceImpl(EnclosureRepo enclosureRepo) {
        this.enclosureRepo = enclosureRepo;
    }

    @Override
    public Enclosure createEnclosure(EnclosureRepo.CreateEnclosureData data) {
        return enclosureRepo.createEnclosure(data);
    }

    @Override
    public List<Enclosure> getAllEnclosures() {
        return enclosureRepo.getAllEnclosures();
    }

    @Override
    public Enclosure getEnclosureById(int id) throws ServiceNotFoundError {
        var enclosure = enclosureRepo.getEnclosureById(id);
        if (enclosure == null) {
            throw NOT_FOUND_ERROR;
        }
        return enclosure;
    }

    @Override
    public void deleteEnclosureById(int id) throws ServiceNotFoundError {
        var enclosure = enclosureRepo.getEnclosureById(id);
        if (enclosure == null) {
            throw NOT_FOUND_ERROR;
        }
        if (!enclosure.getAnimalIds().isEmpty()) {
            throw new ServiceNotFoundError(
                    "Enclosure is not empty, cannot delete");
        }
        enclosureRepo.deleteEnclosureById(id);
    }

}

package gadzet.mini_hw_2.app.services.interfaces;

import java.util.List;

import gadzet.mini_hw_2.app.errors.ServiceError;
import gadzet.mini_hw_2.domain.models.Enclosure;
import gadzet.mini_hw_2.domain.repo.EnclosureRepo;

public interface EnclosureService {
    Enclosure createEnclosure(EnclosureRepo.CreateEnclosureData data)
            throws ServiceError;

    Enclosure getEnclosureById(int id) throws ServiceError;

    List<Enclosure> getAllEnclosures();

    void deleteEnclosureById(int id) throws ServiceError;
}
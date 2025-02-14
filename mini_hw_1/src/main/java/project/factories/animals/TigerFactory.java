package project.factories.animals;

import org.springframework.stereotype.Component;

import project.domains.animals.Tiger;
import project.factories.IInventoryFactory;
import project.factories.params.AnimalParams;
import project.interfaces.IAnimal;

@Component
public class TigerFactory implements IInventoryFactory<AnimalParams, IAnimal> {
    @Override
    public IAnimal create(int id, AnimalParams params) {
        return new Tiger(id, params.foodPerDay, params.isHealthy);
    }
}

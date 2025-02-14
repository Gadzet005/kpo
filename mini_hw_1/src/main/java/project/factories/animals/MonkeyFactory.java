package project.factories.animals;

import org.springframework.stereotype.Component;

import project.domains.animals.Monkey;
import project.factories.IInventoryFactory;
import project.factories.params.AnimalParams;
import project.interfaces.IAnimal;

@Component
public class MonkeyFactory implements IInventoryFactory<AnimalParams, IAnimal> {
    @Override
    public IAnimal create(int id, AnimalParams params) {
        return new Monkey(id, params.foodPerDay, params.isHealthy);
    }
}

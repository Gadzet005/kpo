package project.factories.animals;

import org.springframework.stereotype.Component;

import project.domains.animals.Rabbit;
import project.factories.IInventoryFactory;
import project.factories.params.HerboParams;
import project.interfaces.IAnimal;

@Component
public class RabbitFactory implements IInventoryFactory<HerboParams, IAnimal> {
    @Override
    public IAnimal create(int id, HerboParams params) {
        return new Rabbit(id, params.foodPerDay, params.isHealthy,
                params.kindness);
    }
}

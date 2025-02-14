package project.cli.registrators;

import lombok.AllArgsConstructor;
import project.cli.input.InputService;
import project.factories.IInventoryFactory;
import project.interfaces.IAnimal;
import project.services.AnimalStorage;

@AllArgsConstructor
public class AnimalRegistrator<TParams> implements IInventoryRegistrator {
    private AnimalStorage animals;
    private IInventoryFactory<TParams, IAnimal> factory;
    private InputService<TParams> inputService;

    @Override
    public boolean registerByCLI() {
        var params = inputService.execute();
        if (params == null) {
            return false;
        }
        return animals.addAnimal(factory, params);
    }
}

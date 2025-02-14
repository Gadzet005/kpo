package project.cli.registrators;

import lombok.AllArgsConstructor;
import project.cli.input.InputService;
import project.factories.IInventoryFactory;
import project.interfaces.IInventory;
import project.services.ThingStorage;

@AllArgsConstructor
public class ThingRegistrator<TParams> implements IInventoryRegistrator {
    private ThingStorage things;
    private IInventoryFactory<TParams, IInventory> factory;
    private InputService<TParams> inputService;

    @Override
    public boolean registerByCLI() {
        var params = inputService.execute();
        if (params == null) {
            return false;
        }
        things.addThing(factory, params);
        return true;
    }
}

package project.factories.things;

import project.factories.IInventoryFactory;
import project.factories.params.EmptyParams;
import project.interfaces.IInventory;

import org.springframework.stereotype.Component;

import project.domains.things.Computer;

@Component
public class ComputerFactory
        implements IInventoryFactory<EmptyParams, IInventory> {
    @Override
    public IInventory create(int id, EmptyParams params) {
        return new Computer(id);
    }
}

package project.factories.things;

import org.springframework.stereotype.Component;

import project.domains.things.Table;
import project.factories.IInventoryFactory;
import project.factories.params.EmptyParams;
import project.interfaces.IInventory;

@Component
public class TableFactory
        implements IInventoryFactory<EmptyParams, IInventory> {
    @Override
    public IInventory create(int id, EmptyParams params) {
        return new Table(id);
    }
}

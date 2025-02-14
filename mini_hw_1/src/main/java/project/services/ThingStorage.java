package project.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.factories.IInventoryFactory;
import project.interfaces.IInventory;

@Component
public class ThingStorage {
    private @Autowired InventoryIdCounter idCounter;
    private ArrayList<IInventory> things = new ArrayList<>();

    public <TParams> void addThing(
            IInventoryFactory<TParams, IInventory> factory, TParams params) {
        var thing = factory.create(idCounter.getNextId(), params);
        things.add(thing);
    }

    public IInventory[] getThings() {
        return things.toArray(IInventory[]::new);
    }
}

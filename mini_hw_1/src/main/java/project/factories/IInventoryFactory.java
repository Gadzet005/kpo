package project.factories;

import project.interfaces.IInventory;

public interface IInventoryFactory<TParams, TResult extends IInventory> {
    public TResult create(int id, TParams params);
}

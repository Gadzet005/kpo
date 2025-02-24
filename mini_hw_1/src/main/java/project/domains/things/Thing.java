package project.domains.things;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.interfaces.IInventory;

@AllArgsConstructor
public abstract class Thing implements IInventory {
    @Getter
    protected int id;
    @Getter
    protected String name;

    @Override
    public String getDescription() {
        return name + ", ID: " + id;
    }
}

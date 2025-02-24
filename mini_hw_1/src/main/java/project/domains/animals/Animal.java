package project.domains.animals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.interfaces.IAnimal;

@AllArgsConstructor
public abstract class Animal implements IAnimal {
    @Getter
    private int id;
    @Getter
    private String name;
    @Getter
    private double foodPerDay;
    @Getter
    private boolean isHealthy;
    @Getter
    private boolean isKind;

    @Override
    public String getDescription() {
        return name + ", ID: " + id + ", Food per day: " + foodPerDay;
    }
}

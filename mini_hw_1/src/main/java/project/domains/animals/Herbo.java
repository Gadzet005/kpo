package project.domains.animals;

import lombok.Getter;
import project.Consts;

public abstract class Herbo extends Animal {
    @Getter
    private double kindness;

    public Herbo(int id, String name, double foodPerDay, boolean isHealthy,
            double kindness) {
        super(id, name, foodPerDay, isHealthy,
                kindness >= Consts.KINDNESS_TO_INTERACT);
        this.kindness = kindness;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Kindness: " + kindness;
    }
}

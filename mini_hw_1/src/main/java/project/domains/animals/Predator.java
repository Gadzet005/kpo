package project.domains.animals;

public abstract class Predator extends Animal {
    public Predator(int id, String name, double foodPerDay, boolean isHealthy) {
        super(id, name, foodPerDay, isHealthy, false);
    }
}

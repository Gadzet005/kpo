package project.domains.animals;

public class Wolf extends Predator {
    public Wolf(int id, double foodPerDay, boolean isHealthy) {
        super(id, "Wolf", foodPerDay, isHealthy);
    }
}
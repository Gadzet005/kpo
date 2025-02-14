package project.factories.params;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HerboParams extends AnimalParams {
    public double kindness;

    public HerboParams(double foodPerDay, boolean isHealthy, double kindness) {
        super(foodPerDay, isHealthy);
        this.kindness = kindness;
    }
}

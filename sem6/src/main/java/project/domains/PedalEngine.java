package project.domains;

import lombok.Getter;
import lombok.ToString;
import project.enums.ProductionTypes;
import project.interfaces.IEngine;

/** Двигатель с педальным приводом */
@ToString
@Getter
public class PedalEngine implements IEngine {
    private final int size;

    /** isCompatible */
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return customer.getLegPower() > 5;
    }

    /** PedalEngine */
    public PedalEngine(int size) {
        this.size = size;
    }
}

package project.domains;

import lombok.Getter;
import lombok.ToString;
import project.interfaces.IEngine;

/** Двигатель с педальным приводом */
@ToString
@Getter
public class PedalEngine implements IEngine {
    private final int size;

    /** isCompatible */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }

    /** PedalEngine */
    public PedalEngine(int size) {
        this.size = size;
    }
}

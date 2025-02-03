package project.domains;

import lombok.ToString;
import project.interfaces.IEngine;

/** Двигатель для левитирующего автомобиля */
@ToString
public class LevitatingEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    }
}

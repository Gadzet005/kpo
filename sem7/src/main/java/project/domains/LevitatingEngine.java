package project.domains;

import lombok.ToString;
import project.enums.ProductionTypes;
import project.interfaces.IEngine;

/** Двигатель для левитирующего автомобиля */
@ToString
public class LevitatingEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return customer.getIq() > 300;
    }
}

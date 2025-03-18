package project.domains.engine;

import lombok.ToString;
import project.domains.customer.Customer;
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

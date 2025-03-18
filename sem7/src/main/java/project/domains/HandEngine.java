package project.domains;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import project.enums.ProductionTypes;
import project.interfaces.IEngine;

/** HandEngine */
@Slf4j
@ToString
public class HandEngine implements IEngine {
    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return switch (type) {
        case ProductionTypes.CAR -> customer.getHandPower() > 5;
        case ProductionTypes.CATAMARAN -> customer.getHandPower() > 2;
        case null, default -> {
            log.error("This type of production doesn't exist");
            throw new RuntimeException("This type of production doesn't exist");
        }
        };
    }
}
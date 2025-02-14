package project.domains;

import lombok.ToString;
import project.interfaces.IEngine;

/** HandEngine */
@ToString
public class HandEngine implements IEngine {
    /**
     * Determines if a customer is compatible with the hand engine.
     * 
     * @param customer The customer to check for compatibility.
     * @return true if the customer's hand power is greater than 5, false otherwise.
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}

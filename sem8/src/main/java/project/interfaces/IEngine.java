package project.interfaces;

import project.domains.customer.Customer;
import project.enums.ProductionTypes;

public interface IEngine {
    boolean isCompatible(Customer customer, ProductionTypes type);
}

package project.observers;

import project.domains.Customer;
import project.domains.Report;
import project.enums.ProductionTypes;

public interface SalesObserver {
    void onSale(Customer customer, ProductionTypes productType, int vin);

    void checkCustomers();

    Report buildReport();
}

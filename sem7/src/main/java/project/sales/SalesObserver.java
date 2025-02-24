package project.sales;

import project.domains.Customer;
import project.enums.ProductionTypes;
import project.report.Report;

public interface SalesObserver {
    void onSale(Customer customer, ProductionTypes productType, int vin);

    public void checkCustomers();

    public Report buildReport();
}

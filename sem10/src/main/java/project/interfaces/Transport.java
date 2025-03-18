package project.interfaces;

import project.domains.customer.Customer;

public interface Transport {
    boolean isCompatible(Customer customer);

    int getVin();

    String getEngineType();

    String getTransportType();
}
package project.interfaces;

import project.domains.Customer;

public interface Transport {
    boolean isCompatible(Customer customer);

    int getVin();

    String getEngineType();

    String getTransportType();
}

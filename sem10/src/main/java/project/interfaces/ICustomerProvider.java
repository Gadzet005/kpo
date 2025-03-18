package project.interfaces;

import java.util.List;

import project.domains.customer.Customer;

public interface ICustomerProvider {
    List<Customer> getCustomers();
}

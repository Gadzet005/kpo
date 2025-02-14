package project.interfaces;

import java.util.List;
import project.domains.Customer;

public interface ICustomerProvider {
    List<Customer> getCustomers();
}

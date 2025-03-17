package project.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import project.domains.customer.Customer;
import project.interfaces.ICustomerProvider;

/** Система регистраций покупателей */
@Component
public class CustomerStorage implements ICustomerProvider {
    private List<Customer> customers = new ArrayList<>();

    /** Получает всех зарегистрированных покупателей */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /** Регистрирует покупателя */
    public void addCustomer(Customer customer) {
        customers.add(customer); // просто добавляем покупателя в список
    }

    public Customer getCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public boolean deleteCustomer(String name) {
        return customers.removeIf(customer -> customer.getName().equals(name));
    }
}

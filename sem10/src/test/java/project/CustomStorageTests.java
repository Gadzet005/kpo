package project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.customer.Customer;
import project.services.CustomerStorage;

@SpringBootTest
public class CustomStorageTests {
    @Autowired
    private CustomerStorage customerStorage;

    @Test
    @DisplayName("Тестирует хранилище пользователей")
    void testCustomerStorage() {
        var customer = new Customer("Jane Doe", 85, 100, 115);
        customerStorage.addCustomer(customer);
        var customers = customerStorage.getCustomers();
        assert customers.contains(customer);
    }
}
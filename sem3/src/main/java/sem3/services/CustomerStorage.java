package sem3.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import sem3.domains.Customer;
import sem3.interfaces.ICustomerProvider;

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
}

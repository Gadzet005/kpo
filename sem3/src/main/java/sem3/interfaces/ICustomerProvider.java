package sem3.interfaces;

import java.util.List;
import sem3.domains.Customer;

public interface ICustomerProvider {
  List<Customer> getCustomers(); // метод возвращает коллекцию только для чтения, так как мы не
                                 // хотим давать вызывающему коду возможность изменять список
}

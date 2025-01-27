package sem3.interfaces;

import sem3.domains.Car;
import sem3.domains.Customer;

public interface ICarProvider {
  Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может
                                  // ничего не вернуть
}

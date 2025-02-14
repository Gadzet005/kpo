package sem2;

import java.util.Objects;

/** Сервис для продажи автомобилей HSE */
public class HseCarService {
  private final ICarProvider carProvider;

  private final ICustomerProvider customerProvider;

  public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider) {
    this.carProvider = carProvider;
    this.customerProvider = customersProvider;
  }

  /** Продажа всех автомобилей HSE */
  public void sellCars() {
    // получаем список покупателей
    var customers = customerProvider.getCustomers();
    // пробегаемся по полученному списку
    customers.stream().filter(customer -> Objects.isNull(customer.getCar())).forEach(customer -> {
      var car = carProvider.takeCar(customer);
      if (Objects.nonNull(car)) {
        customer.setCar(car);
      }
    });
  }
}
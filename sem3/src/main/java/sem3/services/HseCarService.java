package sem3.services;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sem3.interfaces.ICarProvider;
import sem3.interfaces.ICustomerProvider;

/** Сервис для продажи автомобилей HSE */
@Component
@RequiredArgsConstructor
public class HseCarService {
  @Autowired private final ICarProvider carProvider;
  @Autowired private final ICustomerProvider customerProvider;

  // public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider) {
  //   this.carProvider = carProvider;
  //   this.customerProvider = customersProvider;
  // }

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
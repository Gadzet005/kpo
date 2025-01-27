package sem3.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import sem3.domains.Car;
import sem3.domains.Customer;
import sem3.interfaces.ICarFactory;
import sem3.interfaces.ICarProvider;

/** Сервис для работы с автомобилями */
@Component
public class CarService implements ICarProvider {
  private final List<Car> cars = new ArrayList<>();

  private int carNumberCounter = 0;

  /** Выдача автомобиля покупателю */
  @Override
  public Car takeCar(Customer customer) {
    var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

    var firstCar = filteredCars.stream().findFirst();

    firstCar.ifPresent(cars::remove);

    return firstCar.orElse(null);
  }

  /** Добавление нового автомобиля */
  public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams) {
    // создаем автомобиль из переданной фабрики
    var car = carFactory.createCar(carParams, // передаем параметры
        ++carNumberCounter // передаем номер - номер будет начинаться с 1
    );

    cars.add(car); // добавляем автомобиль
  }
}

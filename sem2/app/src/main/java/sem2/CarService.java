package sem2;

import java.util.ArrayList;
import java.util.List;

/** Сервис для работы с автомобилями */
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

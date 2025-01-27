package sem3.factories;

import org.springframework.stereotype.Component;
import sem3.domains.Car;
import sem3.domains.HandEngine;
import sem3.interfaces.ICarFactory;
import sem3.params.EmptyEngineParams;

/** Фабрика автомобилей с ручным приводом */
@Component
public class HandCarFactory implements ICarFactory<EmptyEngineParams> {
  @Override
  public Car createCar(EmptyEngineParams carParams, int carNumber) {
    var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

    return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
  }
}

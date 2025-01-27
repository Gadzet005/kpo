package sem3.factories;

import org.springframework.stereotype.Component;
import sem3.domains.Car;
import sem3.domains.LevitatingEngine;
import sem3.interfaces.ICarFactory;
import sem3.params.EmptyEngineParams;

/** Фабрика левитирующих автомобилей */
@Component
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
  @Override
  public Car createCar(EmptyEngineParams carParams, int carNumber) {
    var engine = new LevitatingEngine();

    return new Car(carNumber, engine);
  }
}

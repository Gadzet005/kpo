package sem3.factories;

import org.springframework.stereotype.Component;
import sem3.domains.Car;
import sem3.domains.PedalEngine;
import sem3.interfaces.ICarFactory;
import sem3.params.PedalEngineParams;

/** Фабрика для создания автомобилей с педальным приводом */
@Component
public class PedalCarFactory implements ICarFactory<PedalEngineParams> {
  @Override
  public Car createCar(PedalEngineParams carParams, int carNumber) {
    var engine =
        new PedalEngine(carParams.pedalSize()); // создаем двигатель на основе переданных параметров

    return new Car(carNumber, engine); // возвращаем собранный автомобиль с установленным двигателем
                                       // и определенным номером
  }
}

package project.factories;

import org.springframework.stereotype.Component;
import project.domains.Car;
import project.domains.LevitatingEngine;
import project.interfaces.ICarFactory;
import project.params.EmptyEngineParams;

/** Фабрика левитирующих автомобилей */
@Component
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitatingEngine();

        return new Car(carNumber, engine);
    }
}

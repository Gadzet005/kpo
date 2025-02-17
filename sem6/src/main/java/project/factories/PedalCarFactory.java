package project.factories;

import org.springframework.stereotype.Component;
import project.domains.Car;
import project.domains.PedalEngine;
import project.interfaces.ICarFactory;
import project.params.PedalEngineParams;

/** Фабрика для создания автомобилей с педальным приводом */
@Component
public class PedalCarFactory implements ICarFactory<PedalEngineParams> {
    @Override
    public Car createCar(PedalEngineParams carParams, int carNumber) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(carNumber, engine);
    }
}

package project.factories.cars;

import project.domains.cars.Car;
import project.domains.PedalEngine;
import project.interfaces.cars.CarFactory;
import project.params.PedalEngineParams;
import project.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания машин с {@link PedalEngine} типом двигателя.
 */
@Component
public class PedalCarFactory implements CarFactory<PedalEngineParams> {
    @Override
    public Car create(PedalEngineParams carParams) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(engine);
    }
}

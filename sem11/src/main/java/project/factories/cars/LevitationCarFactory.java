package project.factories.cars;

import project.domains.cars.Car;
import project.domains.LevitationEngine;
import project.interfaces.cars.CarFactory;
import project.params.EmptyEngineParams;
import project.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания машин с {@link LevitationEngine} типом двигателя.
 */
@Component
public class LevitationCarFactory implements CarFactory<EmptyEngineParams> {
    @Override
    public Car create(EmptyEngineParams carParams) {
        var engine = new LevitationEngine();

        return new Car(engine);
    }
}

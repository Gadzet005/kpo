package project.factories.cars;

import project.domains.cars.Car;
import project.domains.HandEngine;
import project.interfaces.cars.CarFactory;
import project.params.EmptyEngineParams;
import project.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания машин с {@link HandEngine} типом двигателя.
 */
@Component
public class HandCarFactory implements CarFactory<EmptyEngineParams> {
    @Override
    public Car create(EmptyEngineParams carParams) {
        var engine = new HandEngine();

        return new Car(engine);
    }
}

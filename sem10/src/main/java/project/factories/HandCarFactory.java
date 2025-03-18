package project.factories;

import org.springframework.stereotype.Component;

import project.domains.car.Car;
import project.domains.engine.HandEngine;
import project.interfaces.ICarFactory;
import project.params.EmptyEngineParams;

/** Фабрика автомобилей с ручным приводом */
@Component
public class HandCarFactory implements ICarFactory<EmptyEngineParams> {
    @Override
    public Car createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо
                                       // параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным
                                           // приводом
    }
}

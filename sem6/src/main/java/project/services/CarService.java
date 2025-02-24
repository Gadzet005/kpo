package project.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import project.domains.Car;
import project.domains.Customer;
import project.interfaces.ICarFactory;
import project.interfaces.ICarProvider;

/** Сервис для работы с автомобилями */
@Slf4j
@Component
public class CarService implements ICarProvider {
    private final List<Car> cars = new ArrayList<>();
    private int carNumberCounter = 0;

    /** Выдача автомобиля покупателю */
    @Override
    public Car takeCar(Customer customer) throws IllegalStateException {
        var filteredCars = cars.stream()
                .filter(car -> car.isCompatible(customer)).toList();

        if (filteredCars.isEmpty()) {
            log.error("No compatible cars found");
            throw new IllegalStateException("No compatible cars found");
        }

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /** Добавление нового автомобиля */
    public <TParams> void addCar(ICarFactory<TParams> carFactory,
            TParams carParams) {
        var car = carFactory.createCar(carParams, ++carNumberCounter);

        cars.add(car);
    }
}

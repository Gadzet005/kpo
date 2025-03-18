package project.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import project.domains.car.Car;
import project.domains.customer.Customer;
import project.interfaces.ICarFactory;
import project.interfaces.ICarProvider;

/** Сервис для работы с автомобилями */
@Slf4j
@Component
public class CarService implements ICarProvider {
    @Getter
    private final List<Car> cars = new ArrayList<>();
    private int carNumberCounter = 0;

    /** Выдача автомобиля покупателю */
    @Override
    public Car takeCar(Customer customer) throws IllegalStateException {
        var filteredCars = cars.stream()
                .filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /** Добавление нового автомобиля */
    public <P> Car addCar(ICarFactory<P> carFactory, P carParams) {
        var car = carFactory.createCar(carParams, ++carNumberCounter);
        cars.add(car);
        return car;
    }

    public void addExistingCar(Car car) {
        cars.add(car);
    }
}

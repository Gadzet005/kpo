package project.factories;

import org.springframework.stereotype.Component;

import project.domains.car.Car;
import project.domains.catamaran.CatamaranWithWheels;
import project.interfaces.ICarFactory;
import project.params.CatamaranWithWheelsParams;

@Component
public class HandCatamaranWithWheelsFactory
        implements ICarFactory<CatamaranWithWheelsParams> {
    @Override
    public Car createCar(CatamaranWithWheelsParams params, int number) {
        return new CatamaranWithWheels(params.catamaran());
    }
}

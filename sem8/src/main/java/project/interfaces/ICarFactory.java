package project.interfaces;

import project.domains.car.Car;

public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}

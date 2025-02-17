package project.interfaces;

import project.domains.Car;

public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}

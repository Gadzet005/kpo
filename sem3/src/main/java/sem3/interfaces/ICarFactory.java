package sem3.interfaces;

import sem3.domains.Car;

public interface ICarFactory<TParams> {
  Car createCar(TParams carParams, int carNumber);
}

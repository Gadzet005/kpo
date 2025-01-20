package sem2;

public interface ICarFactory<TParams> {
  Car createCar(TParams carParams, int carNumber);
}

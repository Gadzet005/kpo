package sem2;

/** Фабрика левитирующих автомобилей */
public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
  @Override
  public Car createCar(EmptyEngineParams carParams, int carNumber) {
    var engine = new LevitatingEngine();

    return new Car(carNumber, engine);
  }
}

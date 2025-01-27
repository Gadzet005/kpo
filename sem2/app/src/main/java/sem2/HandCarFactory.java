package sem2;

/** Фабрика автомобилей с ручным приводом */
public class HandCarFactory implements ICarFactory<EmptyEngineParams> {
  @Override
  public Car createCar(EmptyEngineParams carParams, int carNumber) {
    var engine = new HandEngine(); // Создаем двигатель без каких-либо параметров

    return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
  }
}

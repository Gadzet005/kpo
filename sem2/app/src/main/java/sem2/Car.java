package sem2;

import lombok.Getter;
import lombok.ToString;

/** Автомобиль */
@ToString
public class Car {
  private IEngine engine;

  @Getter private int VIN;

  public Car(int VIN, IEngine engine) {
    this.VIN = VIN;
    this.engine = engine;
  }

  /** проверка совместимости двигателя с покупателем */
  public boolean isCompatible(Customer customer) {
    return this.engine.isCompatible(
        customer); // внутри метода просто вызываем соответствующий метод двигателя
  }
}

package sem3.domains;

import lombok.Getter;
import lombok.ToString;
import sem3.interfaces.IEngine;

/** Автомобиль */
@ToString
public class Car {
  private IEngine engine;

  @Getter private int VIN;

  public Car(int VIN, IEngine engine) {
    this.VIN = VIN;
    this.engine = engine;
  }

  /**
   * проверка совместимости двигателя с покупателем
   * @param customer покупатель
   * @return true если совместим ли двигатель с покупателем, false otherwise
   *  */
  public boolean isCompatible(Customer customer) {
    return this.engine.isCompatible(
        customer); // внутри метода просто вызываем соответствующий метод двигателя
  }
}

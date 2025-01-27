package sem2;

import lombok.ToString;

/** Двигатель для левитирующего автомобиля */
@ToString
public class LevitatingEngine implements IEngine {
  @Override
  public boolean isCompatible(Customer customer) {
    return customer.getIq() > 5;
  }
}

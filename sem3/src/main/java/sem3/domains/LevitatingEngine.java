package sem3.domains;

import lombok.ToString;
import sem3.interfaces.IEngine;

/** Двигатель для левитирующего автомобиля */
@ToString
public class LevitatingEngine implements IEngine {
  @Override
  public boolean isCompatible(Customer customer) {
    return customer.getIq() > 300;
  }
}

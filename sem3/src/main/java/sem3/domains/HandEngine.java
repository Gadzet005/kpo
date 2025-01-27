package sem3.domains;

import lombok.ToString;
import sem3.interfaces.IEngine;

/** Двигатель с ручным приводом */
@ToString
public class HandEngine implements IEngine {
  @Override
  public boolean isCompatible(Customer customer) {
    return customer.getHandPower() > 5;
  }
}

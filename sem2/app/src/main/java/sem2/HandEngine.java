package sem2;

import lombok.ToString;

@ToString
public class HandEngine implements IEngine {
  @Override
  public boolean isCompatible(Customer customer) {
    return customer.getHandPower() > 5;
  }
}

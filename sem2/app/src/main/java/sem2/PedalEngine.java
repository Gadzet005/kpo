package sem2;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PedalEngine implements IEngine {
  private final int size;

  @Override
  public boolean isCompatible(Customer customer) {
    return customer.getLegPower() > 5;
  }

  public PedalEngine(int size) {
    this.size = size;
  }
}

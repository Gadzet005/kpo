package sem2;

public interface IEngine {
  /**
   * Метод для проверки совместимости двигателя с покупателем.
   *
   * @param customer - покупатель, с которым мы сравниваем двигатель
   * @return true, если двигатель подходит покупателю
   */
  boolean isCompatible(Customer customer);
}

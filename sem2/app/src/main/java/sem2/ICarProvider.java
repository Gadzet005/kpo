package sem2;

public interface ICarProvider {
  Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может
                                  // ничего не вернуть
}

package sem3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sem3.domains.Customer;
import sem3.factories.HandCarFactory;
import sem3.factories.LevitatingCarFactory;
import sem3.factories.PedalCarFactory;
import sem3.params.EmptyEngineParams;
import sem3.params.PedalEngineParams;
import sem3.services.CarService;
import sem3.services.CustomerStorage;
import sem3.services.HseCarService;

@SpringBootTest
class ApplicationTests {
  @Autowired private HseCarService hseCarService;
  @Autowired private CarService carService;
  @Autowired private CustomerStorage customStorage;
  @Autowired private HandCarFactory handCarFactory;
  @Autowired private LevitatingCarFactory levCarFactory;
  @Autowired private PedalCarFactory pedalCarFactory;

  @Test
  void testMain() {
    customStorage.addCustomer(new Customer("Bob1", 6, 4, 0));
    customStorage.addCustomer(new Customer("Bob2", 4, 6, 100));
    customStorage.addCustomer(new Customer("Bob3", 6, 6, 300));
    customStorage.addCustomer(new Customer("Bob4", 4, 4, 300));

    carService.addCar(pedalCarFactory, new PedalEngineParams(10));
    carService.addCar(pedalCarFactory, new PedalEngineParams(20));
    carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    carService.addCar(levCarFactory, EmptyEngineParams.DEFAULT);

    customStorage.getCustomers().forEach((customer) -> { System.out.println(customer); });

    hseCarService.sellCars();

    System.out.println("---------------");

    customStorage.getCustomers().forEach((customer) -> { System.out.println(customer); });
  }
}

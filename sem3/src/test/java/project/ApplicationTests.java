package project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.Customer;
import project.factories.HandCarFactory;
import project.factories.LevitatingCarFactory;
import project.factories.PedalCarFactory;
import project.params.EmptyEngineParams;
import project.params.PedalEngineParams;
import project.services.CarService;
import project.services.CustomerStorage;
import project.services.HseCarService;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private HseCarService hseCarService;
    @Autowired
    private CarService carService;
    @Autowired
    private CustomerStorage customStorage;
    @Autowired
    private HandCarFactory handCarFactory;
    @Autowired
    private LevitatingCarFactory levCarFactory;
    @Autowired
    private PedalCarFactory pedalCarFactory;

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

        customStorage.getCustomers().forEach((customer) -> {
            System.out.println(customer);
        });

        hseCarService.sellCars();

        System.out.println("---------------");

        customStorage.getCustomers().forEach((customer) -> {
            System.out.println(customer);
        });
    }
}

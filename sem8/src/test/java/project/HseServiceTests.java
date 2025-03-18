package project;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.customer.Customer;
import project.factories.HandCarFactory;
import project.params.EmptyEngineParams;
import project.services.CarService;
import project.services.CustomerStorage;
import project.services.HseCarService;

@SpringBootTest
public class HseServiceTests {
    @Mock
    private CarService carService;

    @Mock
    private CustomerStorage customerStorage;

    @InjectMocks
    private HseCarService hseService;

    @Autowired
    private HandCarFactory handCarFactory;

    @Test
    void testHseService() {
        when(customerStorage.getCustomers())
                .thenReturn(List.of(new Customer("John Doe", 100, 50, 100),
                        new Customer("Jane Smith", 80, 60, 95)));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        hseService.sellCars();

        Mockito.verify(carService, times(2))
                .takeCar(Mockito.any(Customer.class));

    }
}

package project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.customer.Customer;
import project.factories.HandCarFactory;
import project.params.EmptyEngineParams;
import project.services.CarService;

@SpringBootTest
public class CarServiceTests {
    @Autowired
    private CarService carService;
    @Autowired
    private HandCarFactory handCarFactory;

    @Test
    @DisplayName("Тестирует сервис автомобилей")
    void testCarService() {
        var customer = new Customer("John Doe", 100, 75, 120);

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        var takenCar = carService.takeCar(customer);
        assertNotNull(takenCar);

        assertThrows(IllegalStateException.class,
                () -> carService.takeCar(customer));
    }
}
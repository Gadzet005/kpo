package project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.Customer;
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
        assert takenCar != null;

        takenCar = carService.takeCar(customer);
        assert takenCar == null;
    }
}
package project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.Customer;
import project.factories.HandCarFactory;
import project.params.EmptyEngineParams;
import project.services.CarService;

@SpringBootTest
public class ServiceTests {
    @Autowired
    private CarService service;

    @Test
    void testCarService() {
        var customer = new Customer("John Doe", 100, 75, 120);

        var takenCar = service.takeCar(customer);
        assert takenCar == null;

        var carFactory = new HandCarFactory();
        service.addCar(carFactory, EmptyEngineParams.DEFAULT);

        takenCar = service.takeCar(customer);
        assert takenCar != null;

        takenCar = service.takeCar(customer);
        assert takenCar == null;
    }
}
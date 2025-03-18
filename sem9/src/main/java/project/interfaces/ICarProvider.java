package project.interfaces;

import project.domains.car.Car;
import project.domains.customer.Customer;

public interface ICarProvider {
    Car takeCar(Customer customer);
}

package project.interfaces;

import project.domains.Car;
import project.domains.Customer;

public interface ICarProvider {
    Car takeCar(Customer customer);
}

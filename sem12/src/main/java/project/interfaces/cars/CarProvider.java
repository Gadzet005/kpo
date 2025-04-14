package project.interfaces.cars;

import project.domains.cars.Car;
import project.domains.Customer;

public interface CarProvider {

    /**
     * Метод покупки машины.
     *
     * @param customer - покупатель
     * @return - {@link Car}
     */
    Car takeCar(Customer customer);
}

package project.domains.catamaran;

import lombok.ToString;
import project.domains.car.Car;
import project.domains.customer.Customer;

@ToString
public class CatamaranWithWheels extends Car {
    private Catamaran catamaran;

    public CatamaranWithWheels(Catamaran catamaran) {
        super(catamaran.getVin(), catamaran.getEngine());
        this.catamaran = catamaran;
    }

    @Override
    public boolean isCompatible(Customer customer) {
        return catamaran.isCompatible(customer);
    }
}

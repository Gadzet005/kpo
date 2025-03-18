package project.domains;

import lombok.ToString;

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

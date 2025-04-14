package project.domains;

import project.domains.cars.Car;
import project.domains.catamarans.Catamaran;
import project.enums.ProductionTypes;

public class CatamaranWithWheels extends Car {
    private final Catamaran catamaran;

    public CatamaranWithWheels(Catamaran catamaran) {
        super(catamaran.getVin() + 10000,
                (AbstractEngine) catamaran.getEngine());
        this.catamaran = catamaran;
    }

    @Override
    public boolean isCompatible(Customer customer) {
        // Используем проверку совместимости для автомобилей
        return this.catamaran.getEngine().isCompatible(customer,
                ProductionTypes.CATAMARAN);
    }

    @Override
    public String toString() {
        return "Адаптированный катамаран VIN-" + getVin();
    }
}
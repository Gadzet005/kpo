package project.domains.catamaran;

import lombok.Getter;
import lombok.ToString;
import project.domains.customer.Customer;
import project.enums.ProductionTypes;
import project.interfaces.IEngine;
import project.interfaces.Transport;

@ToString
public class Catamaran implements Transport {
    @Getter
    private IEngine engine;

    @Getter
    private int vin;

    public Catamaran(int vin, IEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CATAMARAN);
    }

    @Override
    public String getEngineType() {
        return this.engine.getClass().getSimpleName();
    }

    @Override
    public String getTransportType() {
        return this.getClass().getSimpleName();
    }
}

package project.domains;

import lombok.Getter;
import lombok.ToString;
import project.enums.ProductionTypes;
import project.interfaces.IEngine;

@ToString
public class Catamaran {
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
}

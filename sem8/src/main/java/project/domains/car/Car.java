package project.domains.car;

import lombok.Getter;
import lombok.ToString;
import project.domains.customer.Customer;
import project.enums.ProductionTypes;
import project.interfaces.IEngine;
import project.interfaces.Transport;

/** Автомобиль */
@ToString
public class Car implements Transport {
    private IEngine engine;

    @Getter
    private int vin;

    public Car(int vin, IEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    /**
     * проверка совместимости двигателя с покупателем
     * 
     * @param customer покупатель
     * @return true если совместим ли двигатель с покупателем, false otherwise
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CAR);
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

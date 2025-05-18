package project.domains.cars;

import project.domains.AbstractEngine;
import project.domains.Customer;
import project.domains.HandEngine;
import project.domains.LevitationEngine;
import project.domains.PedalEngine;
import project.enums.EngineTypes;
import project.enums.ProductionTypes;
import project.interfaces.Engine;
import project.interfaces.Transport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс хранящий информацию о машине.
 */
@Getter
@Setter
@Entity
@Table(name = "cars")
@ToString
@NoArgsConstructor
public class Car implements Transport {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private AbstractEngine engine;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;

    public Car(int vin, AbstractEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    public Car(AbstractEngine engine) {
        this.engine = engine;
    }

    public String getEngineType() {
        if (engine instanceof HandEngine) {
            return EngineTypes.HAND.name();
        }
        if (engine instanceof PedalEngine) {
            return EngineTypes.PEDAL.name();
        }
        if (engine instanceof LevitationEngine) {
            return EngineTypes.LEVITATION.name();
        }
        ;
        throw new RuntimeException("Where is engine???");
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer, ProductionTypes.CAR);
    }

    @Override
    public String getTransportType() {
        return ProductionTypes.CAR.name();
    }
}

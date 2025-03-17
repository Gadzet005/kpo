package project.domains.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.domains.car.Car;
import project.domains.catamaran.Catamaran;

/** Покупатель */
@Getter
@Setter
@ToString
public class Customer {
    private String name;
    private int legPower;
    private int handPower;
    private int iq;

    @Setter
    private Car car;
    @Setter
    private Catamaran catamaran;

    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}

package project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.domains.customer.Customer;
import project.enums.ProductionTypes;
import project.interfaces.ICarProvider;
import project.interfaces.ICustomerProvider;
import project.sales.Sales;
import project.sales.SalesObserver;

/** Сервис для продажи автомобилей HSE */
@Component
@RequiredArgsConstructor
public class HseCarService {
    @Autowired
    private final ICarProvider carProvider;
    @Autowired
    private final ICustomerProvider customerProvider;
    final List<SalesObserver> observers = new ArrayList<>();

    /** Продажа всех автомобилей HSE */
    @Sales
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var car = carProvider.takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.setCar(car);
                        notifyObserversForSale(customer, ProductionTypes.CAR,
                                car.getVin());
                    }
                });
    }

    private void notifyObserversForSale(Customer customer,
            ProductionTypes productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }
}
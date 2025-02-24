package project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.domains.Customer;
import project.enums.ProductionTypes;
import project.interfaces.ICatamaranProvider;
import project.interfaces.ICustomerProvider;
import project.sales.Sales;
import project.sales.SalesObserver;

@Slf4j
@Component
@RequiredArgsConstructor
public class HseCatamaranService {
    @Autowired
    private final ICatamaranProvider catamaranFactory;
    @Autowired
    private final ICustomerProvider customerProvider;

    final List<SalesObserver> observers = new ArrayList<>();

    @Sales
    public void sellCatamarans() {
        var customers = customerProvider.getCustomers();
        if (customers == null) {
            log.error("No customers found");
            throw new IllegalStateException("No customers found");
        }

        customers.stream()
                .filter(customer -> Objects.isNull(customer.getCatamaran()))
                .forEach(customer -> {
                    var catamaran = catamaranFactory.takeCatamaran(customer);
                    if (Objects.nonNull(catamaran)) {
                        customer.setCatamaran(catamaran);
                        notifyObserversForSale(customer,
                                ProductionTypes.CATAMARAN, catamaran.getVin());
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
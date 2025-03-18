package project.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import lombok.Getter;
import project.domains.catamaran.Catamaran;
import project.domains.customer.Customer;
import project.interfaces.ICatamaranFactory;
import project.interfaces.ICatamaranProvider;

@Component
public class CatamaranService implements ICatamaranProvider {
    @Getter
    private final List<Catamaran> catamarans = new ArrayList<>();

    private int catamaranNumberCounter = 0;

    @Override
    public Catamaran takeCatamaran(Customer customer) {
        var filteredCatamarans = catamarans.stream()
                .filter(catamaran -> catamaran.isCompatible(customer)).toList();

        var firstCatamaran = filteredCatamarans.stream().findFirst();

        firstCatamaran.ifPresent(catamarans::remove);

        return firstCatamaran.orElse(null);
    }

    public <TParams> Catamaran addCatamaran(
            ICatamaranFactory<TParams> carFactory, TParams carParams) {
        var car = carFactory.createCar(carParams, ++catamaranNumberCounter);
        catamarans.add(car);
        return car;
    }
}

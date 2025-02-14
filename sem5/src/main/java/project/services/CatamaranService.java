package project.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import project.domains.Catamaran;
import project.domains.Customer;
import project.interfaces.ICatamaranFactory;
import project.interfaces.ICatamaranProvider;

@Component
public class CatamaranService implements ICatamaranProvider {
    private final List<Catamaran> catamarans = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Catamaran takeCatamaran(Customer customer) {
        var filteredCars = catamarans.stream()
                .filter(car -> car.isCompatible(customer)).toList();

        var firstCatamaran = filteredCars.stream().findFirst();

        firstCatamaran.ifPresent(catamarans::remove);

        return firstCatamaran.orElse(null);
    }

    public <TParams> void addCatamaran(ICatamaranFactory<TParams> carFactory,
            TParams carParams) {
        var car = carFactory.createCar(carParams, ++carNumberCounter);

        catamarans.add(car);
    }
}

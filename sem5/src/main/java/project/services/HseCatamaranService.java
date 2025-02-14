package project.services;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import project.interfaces.ICatamaranProvider;
import project.interfaces.ICustomerProvider;

@Component
@RequiredArgsConstructor
public class HseCatamaranService {
    @Autowired
    private final ICatamaranProvider catamaranFactory;
    @Autowired
    private final ICustomerProvider customerProvider;

    public void sellCatamarans() {
        var customers = customerProvider.getCustomers();
        if (customers == null) {
            throw new IllegalStateException("No customers found");
        }

        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var catamaran = catamaranFactory.takeCatamaran(customer);
                    if (Objects.nonNull(catamaran)) {
                        customer.setCatamaran(catamaran);
                    }
                });
    }
}
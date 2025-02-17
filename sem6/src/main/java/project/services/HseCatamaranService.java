package project.services;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.interfaces.ICatamaranProvider;
import project.interfaces.ICustomerProvider;

@Slf4j
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
            log.error("No customers found");
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
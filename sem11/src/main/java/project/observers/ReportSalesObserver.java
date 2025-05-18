package project.observers;

import project.builders.ReportBuilder;
import project.domains.Customer;
import project.domains.Report;
import project.enums.ProductionTypes;
import project.storages.CustomerStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportSalesObserver implements SalesObserver {
    private final CustomerStorage customerStorage;

    private final ReportBuilder reportBuilder = new ReportBuilder();

    public Report buildReport() {
        return reportBuilder.build();
    }

    public void checkCustomers() {
        reportBuilder.addCustomers(customerStorage.getCustomers());
    }

    @Override
    public void onSale(Customer customer, ProductionTypes productType,
            int vin) {
        String message = String.format(
                "Продажа: %s VIN-%d клиенту %s (Сила рук: %d, Сила ног: %d, IQ: %d)",
                productType, vin, customer.getName(), customer.getHandPower(),
                customer.getLegPower(), customer.getIq());
        reportBuilder.addOperation(message);
    }
}

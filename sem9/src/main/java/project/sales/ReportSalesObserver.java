package project.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.domains.customer.Customer;
import project.enums.ProductionTypes;
import project.report.Report;
import project.report.ReportBuilder;
import project.services.CustomerStorage;

@Component
@RequiredArgsConstructor
public class ReportSalesObserver implements SalesObserver {
    @Autowired
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
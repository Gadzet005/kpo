package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.domains.Customer;
import project.factories.HandCatamaranFactory;
import project.params.EmptyEngineParams;
import project.report.ReportBuilder;
import project.services.CatamaranService;
import project.services.CustomerStorage;
import project.services.HseCatamaranService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        var catamaranService = context.getBean(CatamaranService.class);
        var customerStorage = context.getBean(CustomerStorage.class);
        var hseCatamaranService = context.getBean(HseCatamaranService.class);
        var handCarFactory = new HandCatamaranFactory();

        customerStorage.addCustomer(new Customer("Bob1", 6, 4, 0));
        customerStorage.addCustomer(new Customer("Bob2", 4, 6, 100));
        customerStorage.addCustomer(new Customer("Bob3", 6, 6, 300));
        customerStorage.addCustomer(new Customer("Bob4", 4, 4, 300));

        var reportBuilder = new ReportBuilder()
                .addOperation("Инициализация системы")
                .addCustomers(customerStorage.getCustomers());

        catamaranService.addCatamaran(handCarFactory,
                EmptyEngineParams.DEFAULT);
        catamaranService.addCatamaran(handCarFactory,
                EmptyEngineParams.DEFAULT);

        hseCatamaranService.sellCatamarans();

        var report = reportBuilder.addOperation("Продажа автомобилей")
                .addCustomers(customerStorage.getCustomers()).build();

        System.out.println(report.toString());
    }
}

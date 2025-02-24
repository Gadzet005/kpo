package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.domains.Customer;
import project.factories.HandCarFactory;
import project.factories.HandCatamaranFactory;
import project.params.EmptyEngineParams;
import project.sales.ReportSalesObserver;
import project.services.CarService;
import project.services.CatamaranService;
import project.services.CustomerStorage;
import project.services.HseCarService;
import project.services.HseCatamaranService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        var customerStorage = context.getBean(CustomerStorage.class);

        var carService = context.getBean(CarService.class);
        var hseCarService = context.getBean(HseCarService.class);
        var handCarFactory = new HandCarFactory();

        var catamaranService = context.getBean(CatamaranService.class);
        var hseCatamaranService = context.getBean(HseCatamaranService.class);
        var handCatamaranFactory = context.getBean(HandCatamaranFactory.class);

        var salesObserver = context.getBean(ReportSalesObserver.class);
        hseCarService.addObserver(salesObserver);
        hseCatamaranService.addObserver(salesObserver);

        customerStorage.addCustomer(new Customer("Bob1", 100, 100, 0));
        customerStorage.addCustomer(new Customer("Bob2", 100, 100, 100));
        customerStorage.addCustomer(new Customer("Bob3", 100, 100, 300));
        customerStorage.addCustomer(new Customer("Bob4", 100, 100, 300));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        catamaranService.addCatamaran(handCatamaranFactory,
                EmptyEngineParams.DEFAULT);
        catamaranService.addCatamaran(handCatamaranFactory,
                EmptyEngineParams.DEFAULT);
        catamaranService.addCatamaran(handCatamaranFactory,
                EmptyEngineParams.DEFAULT);
        catamaranService.addCatamaran(handCatamaranFactory,
                EmptyEngineParams.DEFAULT);

        hseCarService.sellCars();
        hseCatamaranService.sellCatamarans();

        var report = salesObserver.buildReport();

        System.out.println(report.toString());
    }
}

package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.domains.Customer;
import project.factories.HandCarFactory;
import project.params.EmptyEngineParams;
import project.sales.ReportSalesObserver;
import project.services.CarService;
import project.services.CustomerStorage;
import project.services.HseCarService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        var carService = context.getBean(CarService.class);
        var customerStorage = context.getBean(CustomerStorage.class);
        var hseCarService = context.getBean(HseCarService.class);
        var handCarFactory = new HandCarFactory();

        var salesObserver = context.getBean(ReportSalesObserver.class);
        hseCarService.addObserver(salesObserver);

        customerStorage.addCustomer(new Customer("Bob1", 100, 100, 0));
        customerStorage.addCustomer(new Customer("Bob2", 100, 100, 100));
        customerStorage.addCustomer(new Customer("Bob3", 100, 100, 300));
        customerStorage.addCustomer(new Customer("Bob4", 100, 100, 300));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        hseCarService.sellCars();

        var report = salesObserver.buildReport();

        System.out.println(report.toString());
    }
}

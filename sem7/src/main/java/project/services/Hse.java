package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.domains.Customer;
import project.factories.HandCarFactory;
import project.factories.HandCatamaranFactory;
import project.factories.HandCatamaranWithWheelsFactory;
import project.factories.LevitatingCarFactory;
import project.factories.PedalCarFactory;
import project.params.CatamaranWithWheelsParams;
import project.params.EmptyEngineParams;
import project.params.PedalEngineParams;
import project.sales.ReportSalesObserver;

@Component
public class Hse {
    private CarService carService;
    private CatamaranService catamaranService;

    private CustomerStorage customers;

    private HseCarService hseCarService;
    private HseCatamaranService hseCatamaranService;

    private HandCarFactory handCarFactory;
    private LevitatingCarFactory levCarFactory;
    private PedalCarFactory pedalCarFactory;
    private HandCatamaranFactory handCatamaranFactory;
    private HandCatamaranWithWheelsFactory handCatamaranWithWheelsFactory;

    private ReportSalesObserver salesObserver;

    @Autowired
    public Hse(CarService carService, CatamaranService catamaranService,
            CustomerStorage customers, HseCarService hseCarService,
            HseCatamaranService hseCatamaranService,
            HandCarFactory handCarFactory, LevitatingCarFactory levCarFactory,
            PedalCarFactory pedalCarFactory,
            HandCatamaranFactory handCatamaranFactory,
            ReportSalesObserver salesObserver,
            HandCatamaranWithWheelsFactory handCatamaranWithWheelsFactory) {
        this.carService = carService;
        this.catamaranService = catamaranService;
        this.customers = customers;
        this.hseCarService = hseCarService;
        this.hseCatamaranService = hseCatamaranService;
        this.handCarFactory = handCarFactory;
        this.levCarFactory = levCarFactory;
        this.pedalCarFactory = pedalCarFactory;
        this.handCatamaranFactory = handCatamaranFactory;
        this.salesObserver = salesObserver;
        this.handCatamaranWithWheelsFactory = handCatamaranWithWheelsFactory;

        hseCarService.addObserver(salesObserver);
        hseCatamaranService.addObserver(salesObserver);
    }

    public void sell() {
        hseCarService.sellCars();
        hseCatamaranService.sellCatamarans();
    }

    public void addCustomer(String name, int legPower, int handPower, int iq) {
        customers.addCustomer(new Customer(name, legPower, handPower, iq));
    }

    public void addPedalCar(int pedalSize) {
        carService.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    public void addHandCar() {
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addLevCar() {
        carService.addCar(levCarFactory, EmptyEngineParams.DEFAULT);
    }

    public void addHandCatamaran() {
        catamaranService.addCatamaran(handCatamaranFactory,
                EmptyEngineParams.DEFAULT);
    }

    public void addCatamaranWithWheels() {
        var catamaran = catamaranService.addCatamaran(handCatamaranFactory,
                EmptyEngineParams.DEFAULT);
        carService.addCar(handCatamaranWithWheelsFactory,
                new CatamaranWithWheelsParams(catamaran));
    }

    public String generateReport() {
        return salesObserver.buildReport().toString();
    }
}

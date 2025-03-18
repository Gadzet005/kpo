package project.services;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.domains.customer.Customer;
import project.enums.ReportFormat;
import project.export.report.ReportExporter;
import project.export.report.ReportExporterFactory;
import project.export.transport.TransportExporter;
import project.factories.HandCarFactory;
import project.factories.HandCatamaranFactory;
import project.factories.HandCatamaranWithWheelsFactory;
import project.factories.LevitatingCarFactory;
import project.factories.PedalCarFactory;
import project.interfaces.Transport;
import project.params.CatamaranWithWheelsParams;
import project.params.EmptyEngineParams;
import project.params.PedalEngineParams;
import project.report.Report;
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
    private ReportExporterFactory reportExporterFactory;
    private ReportSalesObserver salesObserver;

    @Autowired
    public Hse(CarService carService, CatamaranService catamaranService,
            CustomerStorage customers, HseCarService hseCarService,
            HseCatamaranService hseCatamaranService,
            HandCarFactory handCarFactory, LevitatingCarFactory levCarFactory,
            PedalCarFactory pedalCarFactory,
            HandCatamaranFactory handCatamaranFactory,
            ReportSalesObserver salesObserver,
            HandCatamaranWithWheelsFactory handCatamaranWithWheelsFactory,
            ReportExporterFactory reportExporterFactory) {
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
        this.reportExporterFactory = reportExporterFactory;

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

    public void exportReport(ReportFormat format, Writer writer) {
        Report report = salesObserver.buildReport();
        ReportExporter exporter = reportExporterFactory.create(format);

        try {
            exporter.export(report, writer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void exportTransport(TransportExporter exporter, Writer writer) {
        List<Transport> transports = Stream
                .concat(carService.getCars().stream(),
                        catamaranService.getCatamarans().stream())
                .toList();
        try {
            exporter.export(transports, writer);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

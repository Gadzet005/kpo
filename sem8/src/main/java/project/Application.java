package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.enums.ReportFormat;
import project.export.transport.CsvTransportExporter;
import project.export.transport.XmlTransportExporter;
import project.services.Hse;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        var context = SpringApplication.run(Application.class, args);
        var hse = context.getBean(Hse.class);

        hse.addCustomer("Ivan1", 6, 4, 150);
        hse.addCustomer("Maksim", 4, 6, 80);
        hse.addCustomer("Petya", 6, 6, 20);
        hse.addCustomer("Nikita", 4, 4, 300);

        hse.addPedalCar(6);
        hse.addHandCar();
        hse.addHandCatamaran();
        hse.addHandCatamaran();
        hse.addCatamaranWithWheels();
        hse.addCatamaranWithWheels();

        File directory = new File("export");
        if (!directory.exists()) {
            directory.mkdir();
        }

        try (FileWriter fileWriter = new FileWriter(
                directory.getPath() + "/transport.csv")) {
            CsvTransportExporter csvTransportExporter = new CsvTransportExporter();
            hse.exportTransport(csvTransportExporter, fileWriter);
        }

        try (FileWriter fileWriter = new FileWriter("export/transport.xml")) {
            XmlTransportExporter xmlTransportExporter = new XmlTransportExporter();
            hse.exportTransport(xmlTransportExporter, fileWriter);
        }

        hse.sell();

        System.out.println(hse.generateReport());

        hse.exportReport(ReportFormat.MARKDOWN, new PrintWriter(System.out));
        try (FileWriter fileWriter = new FileWriter("export/report.MD")) {
            hse.exportReport(ReportFormat.MARKDOWN, fileWriter);
        }
        try (FileWriter fileWriter = new FileWriter("export/report.json")) {
            hse.exportReport(ReportFormat.JSON, fileWriter);
        }
    }
}

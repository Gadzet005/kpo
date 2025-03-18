package project.data_import;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.services.Hse;

@Component
public class CSVTransportImporter {
    private Hse hse;

    @Autowired
    public CSVTransportImporter(Hse hse) {
        this.hse = hse;
    }

    public void importTransport(Reader reader) throws IOException {
        BufferedReader breader = new BufferedReader(reader);
        String line;
        while ((line = breader.readLine()) != null) {
            String[] values = line.split(",");

            int vin;
            try {
                vin = Integer.parseInt(values[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Invalid VIN number: " + values[0]);
            }

            var type = values[1];
            var engineType = values[2];

            if (type.equals("Car")) {
                if (engineType.equals("HandEngine")) {
                    hse.addHandCar();
                    System.out.println("Imported HandCar with VIN: " + vin);
                } else if (engineType.equals("PedalEngine")) {
                    hse.addPedalCar(vin);
                    System.out.println("Imported PedalCar with VIN: " + vin);
                } else {
                    throw new IllegalArgumentException(
                            "Invalid engine type for Car: " + engineType);
                }
            } else if (type.equals("Catamaran")) {
                hse.addHandCatamaran();
                System.out.println("Imported HandCatamaran with VIN: " + vin);
            } else if (type.equals("CatamaranWithWheels")) {
                hse.addCatamaranWithWheels();
                System.out.println(
                        "Imported CatamaranWithWheels with VIN: " + vin);
            } else {
                throw new IllegalArgumentException(
                        "Invalid transport type: " + type);
            }
        }
    }
}

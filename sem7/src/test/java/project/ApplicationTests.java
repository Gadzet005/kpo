package project;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.services.Hse;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private Hse hse;

    @Test
    void testMain() {
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

        hse.sell();

        assertNotNull(hse.generateReport());
    }
}

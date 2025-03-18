package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.services.Hse;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
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

        hse.sell();

        System.out.println(hse.generateReport());
    }
}

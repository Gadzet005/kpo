package project.factories;

import org.springframework.stereotype.Component;
import project.domains.Catamaran;
import project.domains.HandEngine;
import project.interfaces.ICatamaranFactory;
import project.params.EmptyEngineParams;

/** Фабрика автомобилей с ручным приводом */
@Component
public class HandCatamaranFactory
        implements ICatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine(); // Создаем двигатель без каких-либо
                                       // параметров

        return new Catamaran(carNumber, engine); // создаем автомобиль с ручным
        // приводом
    }
}

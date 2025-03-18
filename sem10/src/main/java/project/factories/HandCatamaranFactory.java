package project.factories;

import org.springframework.stereotype.Component;

import project.domains.catamaran.Catamaran;
import project.domains.engine.HandEngine;
import project.interfaces.ICatamaranFactory;
import project.params.EmptyEngineParams;

/** Фабрика автомобилей с ручным приводом */
@Component
public class HandCatamaranFactory
        implements ICatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran createCar(EmptyEngineParams carParams, int carNumber) {
        var engine = new HandEngine();

        return new Catamaran(carNumber, engine);
    }
}

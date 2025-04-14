package project.factories.catamarans;

import project.domains.catamarans.Catamaran;
import project.domains.HandEngine;
import project.interfaces.catamarans.CatamaranFactory;
import project.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link HandEngine} типом двигателя.
 */
@Component
public class HandCatamaranFactory
        implements CatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran create(EmptyEngineParams catamaranParams) {
        var engine = new HandEngine();

        return new Catamaran(engine);
    }
}

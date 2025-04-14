package project.factories.catamarans;

import project.domains.catamarans.Catamaran;
import project.domains.PedalEngine;
import project.interfaces.catamarans.CatamaranFactory;
import project.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link PedalEngine} типом двигателя.
 */
@Component
public class PedalCatamaranFactory
        implements CatamaranFactory<PedalEngineParams> {
    @Override
    public Catamaran create(PedalEngineParams catamaranParams) {
        var engine = new PedalEngine(catamaranParams.pedalSize());

        return new Catamaran(engine);
    }
}

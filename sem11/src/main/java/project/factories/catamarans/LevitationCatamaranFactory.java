package project.factories.catamarans;

import project.domains.catamarans.Catamaran;
import project.domains.LevitationEngine;
import project.interfaces.catamarans.CatamaranFactory;
import project.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link LevitationEngine} типом двигателя.
 */
@Component
public class LevitationCatamaranFactory
        implements CatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran create(EmptyEngineParams catamaranParams) {
        var engine = new LevitationEngine();

        return new Catamaran(engine);
    }
}

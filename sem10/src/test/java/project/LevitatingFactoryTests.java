package project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.factories.PedalCarFactory;
import project.params.PedalEngineParams;

public class LevitatingFactoryTests {
    @Test
    @DisplayName("Тестирует фабрику левитирующих машин")
    void testPedalCarFactory() {
        var carFactory3 = new PedalCarFactory();
        var car3 = carFactory3.createCar(new PedalEngineParams(3), 3);
        assert car3 != null;
    }
}

package project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.factories.LevitatingCarFactory;
import project.params.EmptyEngineParams;

public class PedalFactoryTests {
    @Test
    @DisplayName("Тестирует фабрику педальных машин")
    void testLevitatingCarFactory() {
        var carFactory2 = new LevitatingCarFactory();
        var car2 = carFactory2.createCar(EmptyEngineParams.DEFAULT, 2);
        assert car2 != null;
    }
}

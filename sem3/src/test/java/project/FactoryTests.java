package project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import project.factories.HandCarFactory;
import project.factories.LevitatingCarFactory;
import project.factories.PedalCarFactory;
import project.params.EmptyEngineParams;
import project.params.PedalEngineParams;

@SpringBootTest
public class FactoryTests {
    @Test
    void testCarFactories() {
        var carFactory1 = new HandCarFactory();
        var carFactory2 = new LevitatingCarFactory();
        var carFactory3 = new PedalCarFactory();

        var car1 = carFactory1.createCar(EmptyEngineParams.DEFAULT, 1);
        var car2 = carFactory2.createCar(EmptyEngineParams.DEFAULT, 2);
        var car3 = carFactory3.createCar(new PedalEngineParams(2), 3);

        assert car1.getVin() == 1;
        assert car2.getVin() == 2;
        assert car3.getVin() == 3;
    }
}

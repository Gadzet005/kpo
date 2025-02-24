package project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.factories.HandCarFactory;
import project.params.EmptyEngineParams;

public class HandFactoryTests {
    @Test
    @DisplayName("Тестирует создание машины с ручным двигателем и номером 1.")
    void testHandCarFactory() {
        var carFactory1 = new HandCarFactory();
        var car1 = carFactory1.createCar(EmptyEngineParams.DEFAULT, 1);
        assert car1 != null;
    }
}

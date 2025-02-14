package project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.interfaces.IAnimal;

@SpringBootTest
public class AnimalClinicTest {
    @Autowired
    AnimalClinic animalClinic;
    @Mock
    IAnimal animal;

    @Test
    public void checkHealthyAnimal() {
        Mockito.when(animal.isHealthy()).thenReturn(true);
        assertEquals(animalClinic.checkHealth(animal), true);
    }

    @Test
    public void checkUnhealthyAnimal() {
        Mockito.when(animal.isHealthy()).thenReturn(false);
        assertEquals(animalClinic.checkHealth(animal), false);
    }
}

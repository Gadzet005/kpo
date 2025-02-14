package project.cli.functions;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import project.interfaces.IAnimal;
import project.services.AnimalStorage;

@SpringBootTest
public class GetAllAnimalsTest {
    @Mock
    AnimalStorage animals;
    @InjectMocks
    GetAllAnimals getAllAnimals;
    @Mock
    IAnimal animal;

    @Test
    @DisplayName("Test execute method without animals")
    public void testExecuteWithoutAnimals() {
        when(animals.getAnimals()).thenReturn(new IAnimal[] {});
        getAllAnimals.execute();
    }

    @Test
    @DisplayName("Test execute method with animals")
    public void testExecuteWithAnimals() {
        when(animals.getAnimals()).thenReturn(new IAnimal[] { animal, animal });
        getAllAnimals.execute();
    }
}

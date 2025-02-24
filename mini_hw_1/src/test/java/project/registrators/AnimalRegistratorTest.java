package project.registrators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import project.cli.input.InputService;
import project.cli.registrators.AnimalRegistrator;
import project.factories.IInventoryFactory;
import project.factories.params.AnimalParams;
import project.interfaces.IAnimal;
import project.services.AnimalStorage;

@SpringBootTest
public class AnimalRegistratorTest {
    @Mock
    AnimalStorage animals;
    @Mock
    IInventoryFactory<AnimalParams, IAnimal> factory;
    @Mock
    InputService<AnimalParams> inputService;
    @InjectMocks
    AnimalRegistrator<AnimalParams> animalRegistrator;

    @Test
    @DisplayName("test register by cli")
    void testRegisterByCLI() {
        var params = new AnimalParams(10, true);
        when(animals.addAnimal(Mockito.any(), Mockito.any())).thenReturn(true);
        when(inputService.execute()).thenReturn(params);

        boolean result = animalRegistrator.registerByCLI();

        verify(animals).addAnimal(factory, params);
        assertEquals(true, result);
    }

    @Test
    @DisplayName("test register by cli with null input")
    void testRegisterByCLIWithNullInput() {
        AnimalParams params = null;
        when(inputService.execute()).thenReturn(params);

        boolean result = animalRegistrator.registerByCLI();

        verify(animals, Mockito.never()).addAnimal(factory, params);
        assertEquals(false, result);
    }
}

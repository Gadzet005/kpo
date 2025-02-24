package project.registrators;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import project.cli.input.InputService;
import project.cli.registrators.ThingRegistrator;
import project.factories.IInventoryFactory;
import project.factories.params.EmptyParams;
import project.interfaces.IInventory;
import project.services.ThingStorage;

@SpringBootTest
public class ThingRegistratorTest {
    @Mock
    ThingStorage things;
    @Mock
    IInventoryFactory<EmptyParams, IInventory> factory;
    @Mock
    InputService<EmptyParams> inputService;
    @InjectMocks
    ThingRegistrator<EmptyParams> animalRegistrator;

    @Test
    @DisplayName("test register by cli")
    void testRegisterByCLI() {
        var params = EmptyParams.DEFAULT;
        when(inputService.execute()).thenReturn(params);

        animalRegistrator.registerByCLI();

        verify(things).addThing(factory, params);
    }
}

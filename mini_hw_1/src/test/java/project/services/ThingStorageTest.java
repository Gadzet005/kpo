package project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.IInventoryFactory;
import project.factories.params.EmptyParams;
import project.interfaces.IInventory;

@SpringBootTest
public class ThingStorageTest {
    @Mock
    InventoryIdCounter counter;
    @InjectMocks
    ThingStorage thingStorage;

    @SuppressWarnings("unchecked")
    IInventoryFactory<Object, IInventory> inventoryFactory = Mockito
            .mock(IInventoryFactory.class);

    IInventory thing = Mockito.mock(IInventory.class);

    @BeforeEach
    public void setup() {
        Mockito.when(counter.getNextId()).thenReturn(1);
        Mockito.when(inventoryFactory.create(Mockito.anyInt(), Mockito.any()))
                .thenReturn(thing);
    }

    @Test
    @DisplayName("Add a thing to the storage")
    public void testAddThing() {
        thingStorage.addThing(inventoryFactory, EmptyParams.DEFAULT);
        Mockito.verify(inventoryFactory).create(1, EmptyParams.DEFAULT);
        Mockito.verify(counter).getNextId();
    }

    @Test
    @DisplayName("Get all things")
    public void testGetThings() {
        thingStorage.addThing(inventoryFactory, EmptyParams.DEFAULT);
        thingStorage.addThing(inventoryFactory, EmptyParams.DEFAULT);
        thingStorage.addThing(inventoryFactory, EmptyParams.DEFAULT);
        IInventory[] things = thingStorage.getThings();

        assertEquals(things.length, 3);
    }
}

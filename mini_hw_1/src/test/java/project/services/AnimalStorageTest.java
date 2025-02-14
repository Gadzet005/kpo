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
import project.interfaces.IAnimal;

@SpringBootTest
public class AnimalStorageTest {
    @Mock
    AnimalClinic animalClinic;
    @Mock
    InventoryIdCounter idCounter;
    @InjectMocks
    AnimalStorage animalStorage;

    IAnimal animal = Mockito.mock(IAnimal.class);
    IAnimal kindAnimal = Mockito.mock(IAnimal.class);

    @SuppressWarnings("unchecked")
    IInventoryFactory<Object, IAnimal> animalFactory = Mockito
            .mock(IInventoryFactory.class);
    @SuppressWarnings("unchecked")
    IInventoryFactory<Object, IAnimal> kindAnimalFactory = Mockito
            .mock(IInventoryFactory.class);

    @BeforeEach
    public void setup() {
        Mockito.when(idCounter.getNextId()).thenReturn(1);
        Mockito.when(animalClinic.checkHealth(Mockito.any())).thenReturn(true);

        Mockito.when(animalFactory.create(Mockito.anyInt(), Mockito.any()))
                .thenReturn(animal);
        Mockito.when(kindAnimalFactory.create(Mockito.anyInt(), Mockito.any()))
                .thenReturn(kindAnimal);

        Mockito.when(kindAnimal.isKind()).thenReturn(true);
        Mockito.when(animal.isKind()).thenReturn(false);
    }

    @Test
    @DisplayName("Add a healthy animal to the animal storage")
    public void testAddHealthyAnimal() {
        boolean result = animalStorage.addAnimal(animalFactory,
                EmptyParams.DEFAULT);

        Mockito.verify(animalClinic).checkHealth(animal);
        Mockito.verify(animalFactory).create(1, EmptyParams.DEFAULT);
        Mockito.verify(idCounter).getNextId();
        assertEquals(result, true);
    }

    @Test
    @DisplayName("Add an unhealthy animal to the animal storage")
    public void testAddUnhealthyAnimal() {
        Mockito.when(animalClinic.checkHealth(Mockito.any())).thenReturn(false);

        boolean result = animalStorage.addAnimal(animalFactory,
                EmptyParams.DEFAULT);

        Mockito.verify(animalClinic).checkHealth(animal);
        Mockito.verify(animalFactory).create(1, EmptyParams.DEFAULT);
        Mockito.verify(idCounter).getNextId();
        assertEquals(result, false);
    }

    @Test
    @DisplayName("Get all animals")
    public void testGetAnimals() {
        animalStorage.addAnimal(animalFactory, EmptyParams.DEFAULT);
        animalStorage.addAnimal(kindAnimalFactory, EmptyParams.DEFAULT);
        animalStorage.addAnimal(animalFactory, EmptyParams.DEFAULT);
        IAnimal[] animals = animalStorage.getAnimals();

        assertEquals(animals.length, 3);
    }

    @Test
    @DisplayName("Get all kind animals")
    public void testGetKindAnimals() {
        animalStorage.addAnimal(kindAnimalFactory, EmptyParams.DEFAULT);
        animalStorage.addAnimal(animalFactory, EmptyParams.DEFAULT);
        animalStorage.addAnimal(kindAnimalFactory, EmptyParams.DEFAULT);
        IAnimal[] animals = animalStorage.getKindAnimals();

        assertEquals(animals.length, 2);
        assertEquals(animals[0].isKind(), true);
        assertEquals(animals[1].isKind(), true);
    }

    @Test
    @DisplayName("Get food per day")
    public void testGetFoodPerDay() {
        Mockito.when(animal.getFoodPerDay()).thenReturn(10.0);
        Mockito.when(kindAnimal.getFoodPerDay()).thenReturn(20.0);
        animalStorage.addAnimal(animalFactory, EmptyParams.DEFAULT);
        animalStorage.addAnimal(kindAnimalFactory, EmptyParams.DEFAULT);
        animalStorage.addAnimal(animalFactory, EmptyParams.DEFAULT);
        double foodPerDay = animalStorage.getFoodPerDay();

        assertEquals(foodPerDay, 40.0);
    }
}

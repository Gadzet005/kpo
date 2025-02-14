package project.registrators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import project.cli.registrators.IInventoryRegistrator;
import project.cli.registrators.RegistratorStorage;

public class RegistratorStorageTest {
    @Mock
    IInventoryRegistrator registrator;
    @Mock
    IInventoryRegistrator anotherRegistrator;

    @Test
    @DisplayName("test get registrator")
    void testGetRegistrator() {
        var storage = new RegistratorStorage();
        storage.addRegistrator("Pedal", registrator);
        storage.addRegistrator("Scooter", anotherRegistrator);

        var result1 = storage.getRegistrator("Pedal");
        var result2 = storage.getRegistrator("Scooter");

        assertEquals(registrator, result1);
        assertEquals(anotherRegistrator, result2);
    }

    @Test
    @DisplayName("test get available names")
    void testGetAvailableNames() {
        var storage = new RegistratorStorage();
        storage.addRegistrator("Pedal", registrator);
        storage.addRegistrator("Scooter", anotherRegistrator);

        var result = storage.getAvailableNames();

        assertEquals(2, result.length);
    }
}

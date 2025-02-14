package project.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import project.cli.base.CLIFunction;
import project.cli.base.CLIFunctionStorage;

@SpringBootTest
public class CLIFunctionStorageTest {
    CLIFunctionStorage storage = new CLIFunctionStorage();
    CLIFunction function = Mockito.mock(CLIFunction.class);
    CLIFunction anotherFunction = Mockito.mock(CLIFunction.class);

    @Test
    @DisplayName("Get a registred function by name")
    public void testRegisterFunctionByName() {
        storage.registerFunction("testFunction1", anotherFunction);
        storage.registerFunction("testFunction2", anotherFunction);
        storage.registerFunction("testFunction3", function);

        CLIFunction retrievedFunction = storage.getFunction("testFunction3");

        assertNotNull(retrievedFunction);

        retrievedFunction.execute();
        Mockito.verify(function).execute();
    }

    @Test
    @DisplayName("Get a registred function by index")
    public void testGetFunctionByIndex() {
        storage.registerFunction("testFunction1", anotherFunction);
        storage.registerFunction("testFunction2", function);
        storage.registerFunction("testFunction3", anotherFunction);
        CLIFunction retrievedFunction = storage.getFunction(1);

        assertNotNull(retrievedFunction);

        retrievedFunction.execute();
        Mockito.verify(function).execute();
    }

    @Test
    @DisplayName("Get a non-registered function by name")
    public void testGetNonRegisteredFunctionByName() {
        CLIFunction retrievedFunction = storage
                .getFunction("nonRegisteredFunction");
        assertNull(retrievedFunction);
    }

    @Test
    @DisplayName("Get a non-registered function by index")
    public void testGetNonRegisteredFunctionByIndex() {
        CLIFunction retrievedFunction = storage.getFunction(0);
        assertNull(retrievedFunction);
    }

    @Test
    @DisplayName("Get all registered functions")
    public void testGetFunctionNames() {
        storage.registerFunction("testFunction1", function);
        storage.registerFunction("testFunction2", function);
        storage.registerFunction("testFunction3", function);

        String[] functionNames = storage.getFunctionNames();

        assertEquals(functionNames.length, 3);
        assertEquals(functionNames[0], "testFunction1");
        assertEquals(functionNames[1], "testFunction2");
        assertEquals(functionNames[2], "testFunction3");
    }

}

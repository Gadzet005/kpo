package project.cli.base;

import java.util.ArrayList;

/**
 * Storage for CLI functions.
 */
public class CLIFunctionStorage {
    private ArrayList<FunctionStorageRecord> functions = new ArrayList<>();

    /**
     * Registers a new function with the given name in the function storage.
     *
     * @param name     The name of the function to be registered.
     * @param function The function to be associated with the given name.
     */
    public void registerFunction(String name, CLIFunction function) {
        functions.add(new FunctionStorageRecord(name, function));
    }

    /**
     * Retrieves a function from the storage based on the given name.
     *
     * @param name The name of the function to retrieve.
     * @return The function associated with the given name, or null if not
     *         found.
     */
    public CLIFunction getFunction(String name) {
        for (FunctionStorageRecord record : functions) {
            if (record.name().equalsIgnoreCase(name)) {
                return record.function();
            }
        }
        return null;
    }

    /**
     * Retrieves a function from the storage based on its index.
     *
     * @param index The index of the function to retrieve.
     * @return The CLIFunction associated with the given index or null if index
     *         out of range.
     */
    public CLIFunction getFunction(int index) {
        if (index < 0 || index >= functions.size()) {
            return null;
        }
        var record = functions.get(index);
        return record.function();
    }

    /**
     * Returns the names of all registered functions.
     *
     * @return A list of registered function names.
     */
    public String[] getFunctionNames() {
        String[] names = new String[functions.size()];
        for (int i = 0; i < functions.size(); i++) {
            names[i] = functions.get(i).name();
        }
        return names;
    }
}

package project.cli.registrators;

import java.util.HashMap;

public class RegistratorStorage {
    private HashMap<String, IInventoryRegistrator> registrators = new HashMap<>();

    public void addRegistrator(String name, IInventoryRegistrator registrator) {
        registrators.put(name, registrator);
    }

    public IInventoryRegistrator getRegistrator(String name) {
        return registrators.get(name);
    }

    public String[] getAvailableNames() {
        return registrators.keySet().toArray(new String[0]);
    }
}

package project.services;

import org.springframework.stereotype.Component;

@Component
public class InventoryIdCounter {
    private int idCounter = 0;

    public int getNextId() {
        return idCounter++;
    }

    public void resetCounter() {
        idCounter = 0;
    }
}

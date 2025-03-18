package project.utils;

public class IdCounter {
    private int counter = 0;

    public int getNextId() {
        return counter++;
    }

    public void resetCounter() {
        counter = 0;
    }
}

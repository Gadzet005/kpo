package project.services;

import org.springframework.stereotype.Component;
import project.interfaces.IAnimal;

@Component
public class AnimalClinic {
    public boolean checkHealth(IAnimal animal) {
        return animal.isHealthy();
    }
}

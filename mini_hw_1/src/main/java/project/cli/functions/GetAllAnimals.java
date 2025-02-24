package project.cli.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.base.CLIFunction;
import project.services.AnimalStorage;

@Component
public class GetAllAnimals implements CLIFunction {
    private @Autowired AnimalStorage animals;

    public void execute() {
        var list = animals.getAnimals();
        if (list.length == 0) {
            System.out.println("No animals found in the zoo.");
            return;
        }

        System.out.println("Animals in the zoo:");
        for (var animal : list) {
            System.out.println(animal.getDescription());
        }
    }
}
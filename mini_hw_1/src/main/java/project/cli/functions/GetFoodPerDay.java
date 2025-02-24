package project.cli.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.base.CLIFunction;
import project.services.AnimalStorage;

@Component
public class GetFoodPerDay implements CLIFunction {
    private @Autowired AnimalStorage animals;

    public void execute() {
        System.out.println("Total food consumption per day: "
                + animals.getFoodPerDay() + " kg");
    }
}

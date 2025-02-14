package project.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import project.cli.base.CLIFunction;
import project.cli.base.CLIFunctionStorage;
import project.cli.functions.AddAnimal;
import project.cli.functions.AddThing;
import project.cli.functions.GetAllAnimals;
import project.cli.functions.GetAllThings;
import project.cli.functions.GetFoodPerDay;
import project.cli.functions.GetKindAnimals;

@Component
public class AppCLI {
    private @Autowired ApplicationContext context;
    private @Getter CLIFunctionStorage functions = new CLIFunctionStorage();

    @PostConstruct
    private void init() {
        register("Get all animals in the zoo", GetAllAnimals.class);
        register("Get all kind animals in the zoo", GetKindAnimals.class);
        register("Get all things in the zoo", GetAllThings.class);
        register("Get food per day for all animals in the zoo",
                GetFoodPerDay.class);
        register("Add an animal to the zoo", AddAnimal.class);
        register("Add inventory to the zoo", AddThing.class);
    }

    private void register(String name, Class<? extends CLIFunction> function) {
        functions.registerFunction(name, context.getBean(function));
    }
}
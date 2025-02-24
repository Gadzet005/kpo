package project.cli.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.base.CLIFunction;
import project.services.ThingStorage;

@Component
public class GetAllThings implements CLIFunction {
    private @Autowired ThingStorage things;

    public void execute() {
        var list = things.getThings();
        if (list.length == 0) {
            System.out.println("No things found in the zoo.");
        } else {
            System.out.println("Things in the zoo:");
            for (var thing : list) {
                System.out.println(thing.getDescription());
            }
        }
    }
}

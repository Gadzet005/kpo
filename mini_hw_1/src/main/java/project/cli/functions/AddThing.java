package project.cli.functions;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import project.cli.base.CLIFunction;
import project.cli.input.InputService;
import project.cli.input.ThingInputService;
import project.cli.input.fields.ChoiceField;
import project.cli.registrators.RegistratorStorage;
import project.cli.registrators.ThingRegistrator;
import project.factories.IInventoryFactory;
import project.factories.things.ComputerFactory;
import project.factories.things.TableFactory;
import project.interfaces.IInventory;
import project.services.ThingStorage;

@Component
public class AddThing implements CLIFunction {
    private @Autowired ApplicationContext context;
    private @Autowired Scanner reader;
    private RegistratorStorage registrators = new RegistratorStorage();

    @PostConstruct
    private void init() {
        addThingRegistrator("Computer", ComputerFactory.class,
                ThingInputService.class);
        addThingRegistrator("Table", TableFactory.class,
                ThingInputService.class);
    }

    // @formatter:off
    private <
            TParams,
            TFactory extends IInventoryFactory<TParams, IInventory>, 
            TInputService extends InputService<TParams>
        > 
        void addThingRegistrator(
            String name, Class<TFactory> factoryType,
            Class<TInputService> inputServiceType
    ) { 
        var things = context.getBean(ThingStorage.class);
        var factory = context.getBean(factoryType);
        var inputService = context.getBean(inputServiceType);
        var registrator = new ThingRegistrator<TParams>(things, factory, inputService);

        registrators.addRegistrator(name, registrator);
    }
    // @formatter:on

    public void execute() {
        var thingType = (String) ChoiceField.builder().name("Thing type")
                .choices(registrators.getAvailableNames()).build()
                .execute(reader);
        var registrator = registrators.getRegistrator(thingType);

        var result = registrator.registerByCLI();
        if (result) {
            System.out.println("Successfully added " + thingType + ".");
        } else {
            System.out.println("Failed to add " + thingType + ".");
        }
    }
}

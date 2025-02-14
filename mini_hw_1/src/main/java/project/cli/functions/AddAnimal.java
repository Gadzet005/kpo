package project.cli.functions;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import project.cli.base.CLIFunction;
import project.cli.input.AnimalInputService;
import project.cli.input.HerboInputService;
import project.cli.input.InputService;
import project.cli.input.fields.ChoiceField;
import project.cli.registrators.AnimalRegistrator;
import project.cli.registrators.RegistratorStorage;
import project.factories.IInventoryFactory;
import project.factories.animals.MonkeyFactory;
import project.factories.animals.RabbitFactory;
import project.factories.animals.TigerFactory;
import project.factories.animals.WolfFactory;
import project.factories.params.AnimalParams;
import project.interfaces.IAnimal;
import project.services.AnimalStorage;

@Component
public class AddAnimal implements CLIFunction {
    private @Autowired ApplicationContext context;
    private @Autowired Scanner reader;
    private RegistratorStorage registrators = new RegistratorStorage();

    @PostConstruct
    private void init() {
        addAnimalRegistrator("Rabbit", RabbitFactory.class,
                HerboInputService.class);
        addAnimalRegistrator("Wolf", WolfFactory.class,
                AnimalInputService.class);
        addAnimalRegistrator("Monkey", MonkeyFactory.class,
                AnimalInputService.class);
        addAnimalRegistrator("Tiger", TigerFactory.class,
                AnimalInputService.class);
    }

    // @formatter:off
    private <
            TParams extends AnimalParams,
            TFactory extends IInventoryFactory<TParams, IAnimal>, 
            TInputService extends InputService<TParams>
        > 
        void addAnimalRegistrator(
            String name, Class<TFactory> factoryType,
            Class<TInputService> inputServiceType
    ) { 
        var animals = context.getBean(AnimalStorage.class);
        var factory = context.getBean(factoryType);
        var inputService = context.getBean(inputServiceType);
        var registrator = new AnimalRegistrator<TParams>(animals, factory, inputService);

        registrators.addRegistrator(name, registrator);
    }
    // @formatter:on

    public void execute() {
        var animalType = (String) ChoiceField.builder().name("Animal type")
                .choices(registrators.getAvailableNames()).build()
                .execute(reader);
        var registrator = registrators.getRegistrator(animalType);

        var result = registrator.registerByCLI();
        if (result) {
            System.out.println("Animal registered successfully.");
        } else {
            System.out
                    .println("The veterinary clinic did not allow the animal.");
        }
    }
}

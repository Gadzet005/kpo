package project.cli.input;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.fields.BoolField;
import project.cli.input.fields.DoubleField;
import project.factories.params.AnimalParams;

@Component
public class AnimalInputService implements InputService<AnimalParams> {
    private @Autowired Scanner reader;

    @Override
    public AnimalParams execute() {
        var foodPerDay = DoubleField.builder().name("Food per day")
                .minValue(0.0).build().execute(reader);
        var isHealthy = BoolField.builder().name("Is healthy").build()
                .execute(reader);
        return new AnimalParams(foodPerDay, isHealthy);
    }
}

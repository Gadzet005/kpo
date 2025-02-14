package project.cli.input;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.fields.DoubleField;
import project.factories.params.HerboParams;

@Component
public class HerboInputService implements InputService<HerboParams> {
    private @Autowired Scanner reader;
    private @Autowired AnimalInputService animalService;

    @Override
    public HerboParams execute() {
        var animalParams = animalService.execute();
        var kindness = DoubleField.builder().name("Kindness").minValue(0.0)
                .maxValue(10.0).build().execute(reader);
        return new HerboParams(animalParams.foodPerDay, animalParams.isHealthy,
                kindness);
    }
}

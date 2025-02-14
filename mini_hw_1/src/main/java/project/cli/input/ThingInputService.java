package project.cli.input;

import org.springframework.stereotype.Component;

import project.factories.params.EmptyParams;

@Component
public class ThingInputService implements InputService<EmptyParams> {
    @Override
    public EmptyParams execute() {
        return EmptyParams.DEFAULT;
    }
}

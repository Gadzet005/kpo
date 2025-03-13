package project.cli.input;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class StringField extends InputField<String> {
    @Builder
    public StringField(String name, String description, String defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    protected ValidationResult<String> handleInput(String input) {
        if (input.isEmpty() && defaultValue != null) {
            return ValidationResult.valid(defaultValue);
        }
        return ValidationResult.valid(input.strip());
    }
}

package project.cli.input;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class StringField extends InputField<String> {
    @Builder
    public StringField(String name, String description, String defaultValue,
            String defaultLabel, boolean isNullable) {
        super(name, description, defaultValue, defaultLabel, isNullable);
    }

    @Override
    public ValidationResult<String> validate(String str) {
        return ValidationResult.valid(str.strip());
    }
}

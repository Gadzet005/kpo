package project.cli.input;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class BoolField extends InputField<Boolean> {
    @Builder
    public BoolField(String name, String description, Boolean defaultValue) {
        super(name, description, defaultValue);
        if (this.defaultValue == null) {
            this.defaultValue = true;
        }
        if (this.description == null) {
            this.description = "t/f";
        }
    }

    @Override
    protected ValidationResult<Boolean> handleInput(String input) {
        if (input.equalsIgnoreCase("t")) {
            return ValidationResult.valid(true);
        } else if (input.equalsIgnoreCase("f")) {
            return ValidationResult.valid(false);
        } else {
            return ValidationResult
                    .invalid("Invalid boolean value. Please enter 't' or 'f'.");
        }
    }
}

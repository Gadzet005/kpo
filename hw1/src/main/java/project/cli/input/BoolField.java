package project.cli.input;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class BoolField extends InputField<Boolean> {
    @Builder
    public BoolField(String name, String description, Boolean defaultValue,
            String defaultLabel, boolean isNullable) {
        super(name, description, defaultValue, defaultLabel, isNullable);
        if (this.defaultValue == null) {
            this.defaultValue = true;
        }
        if (this.description == null) {
            this.description = "t/f";
        }
    }

    @Override
    public ValidationResult<Boolean> validate(String str) {
        if (str.equalsIgnoreCase("t")) {
            return ValidationResult.valid(true);
        } else if (str.equalsIgnoreCase("f")) {
            return ValidationResult.valid(false);
        } else {
            return ValidationResult
                    .invalid("Invalid boolean value. Please enter 't' or 'f'.");
        }
    }
}

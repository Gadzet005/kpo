package project.cli.input;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class IntField extends InputField<Integer> {
    private Integer minValue;
    private Integer maxValue;

    @Builder
    public IntField(String name, String description, Integer defaultValue,
            String defaultLabel, Integer minValue, Integer maxValue,
            boolean isNullable) {
        super(name, description, defaultValue, defaultLabel, isNullable);
        this.minValue = minValue;
        this.maxValue = maxValue;
        if (description == null) {
            this.description = "int";
        }
    }

    @Override
    public ValidationResult<Integer> validate(String str) {
        try {
            var value = Integer.parseInt(str);
            if (minValue != null && value < minValue) {
                return ValidationResult.invalid(String.format(
                        "Value must be greater than or equal to %s", minValue));
            }
            if (maxValue != null && value > maxValue) {
                return ValidationResult.invalid(String.format(
                        "Value must be less than or equal to %s", maxValue));
            }
            return ValidationResult.valid(value);
        } catch (NumberFormatException e) {
            return ValidationResult.invalid("Invalid double value");
        }
    }
}

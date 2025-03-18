package project.cli.input;

import java.text.DecimalFormat;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class DoubleField extends InputField<Double> {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private Double minValue;
    private Double maxValue;

    @Builder
    public DoubleField(String name, String description, Double defaultValue,
            String defaultLabel, Double minValue, Double maxValue,
            boolean isNullable) {
        super(name, description, defaultValue, defaultLabel, isNullable);
        this.minValue = minValue;
        this.maxValue = maxValue;
        if (description == null) {
            this.description = "double";
        }
    }

    @Override
    public ValidationResult<Double> validate(String str) {
        try {
            var value = Double.parseDouble(str);
            if (minValue != null && value < minValue) {
                return ValidationResult.invalid(String.format(
                        "Value must be greater than or equal to %s",
                        decimalFormat.format(minValue)));
            }
            if (maxValue != null && value > maxValue) {
                return ValidationResult.invalid(
                        String.format("Value must be less than or equal to %s",
                                decimalFormat.format(maxValue)));
            }
            return ValidationResult.valid(value);
        } catch (NumberFormatException e) {
            return ValidationResult.invalid("Invalid double value");
        }
    }
}

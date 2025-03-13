package project.cli.input;

import java.text.DecimalFormat;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class DoubleField extends InputField<Double> {
    private Double minValue;
    private Double maxValue;

    @Builder
    public DoubleField(String name, String description, Double defaultValue,
            Double minValue, Double maxValue) {
        super(name, description, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
        if (description == null) {
            this.description = "double";
        }
    }

    @Override
    protected ValidationResult<Double> handleInput(String input) {
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            var value = Double.parseDouble(input);
            if (minValue != null && value < minValue) {
                return ValidationResult.invalid(String.format(
                        "Value must be greater than or equal to %s",
                        df.format(minValue)));
            }
            if (maxValue != null && value > maxValue) {
                return ValidationResult.invalid(
                        String.format("Value must be less than or equal to %s",
                                df.format(maxValue)));
            }
            return ValidationResult.valid(value);
        } catch (NumberFormatException e) {
            return ValidationResult.invalid("Invalid double value");
        }
    }
}

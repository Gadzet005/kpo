package project.cli.input;

import java.util.function.Function;

import lombok.Builder;
import project.cli.input.base.InputField;
import project.cli.input.base.ValidationResult;

public class ChoiceField<T> extends InputField<T> {
    private T[] choices;
    private Function<T, String> choiceFormat;

    @Builder
    public ChoiceField(String name, String description, T[] choices,
            Function<T, String> choiceFormat) {
        super(name, description, choices.length > 0 ? choices[0] : null);
        this.choices = choices;
        this.choiceFormat = choiceFormat;
        if (this.description == null) {
            this.description = "enter number";
        }
        if (choiceFormat == null) {
            this.choiceFormat = Object::toString;
        }
    }

    @Override
    protected String getDefaultValueLabel() {
        return choiceFormat.apply(defaultValue);
    }

    @Override
    protected String getLabel() {
        var builder = new StringBuilder();
        builder.append(super.getLabel());
        builder.append("\n");
        for (int i = 0; i < choices.length; i++) {
            builder.append(i + 1);
            builder.append(". ");
            builder.append(choiceFormat.apply(choices[i]));
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    protected ValidationResult<T> handleInput(String input) {
        try {
            var choiceIndex = Integer.parseInt(input) - 1;
            if (choiceIndex < 0 || choiceIndex >= choices.length) {
                return ValidationResult.invalid("Invalid choice");
            }
            return ValidationResult.valid(choices[choiceIndex]);
        } catch (NumberFormatException e) {
            return ValidationResult.invalid("Invalid choice number");
        }
    }
}
